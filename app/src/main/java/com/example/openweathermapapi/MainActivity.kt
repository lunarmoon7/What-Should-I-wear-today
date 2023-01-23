package com.example.openweathermapapi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.location.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openweathermapapi.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    /* 추후에 GPS에서 불러온 위도, 경도 좌표로 초기화할 변수임 */
    var lat : String = (37.554752).toString() // 위도
    var lon: String = (126.970631).toString() // 경도

    var loc = LatLng(lat.toDouble(), lon.toDouble()) // 위도&경도를 LatLng로 저장
    var tempForIntent : Double ?= null
    var humidityForIntent : Int ?= null
    var windSpeedForIntent : Double ?= null

    /* GPS, NETWORK를 이용한 위,경도 설정을 위한 변수 */
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationRequest2 : LocationRequest
    lateinit var locationCallback : LocationCallback
    /* */

    var startUpdate = false // 위치 정보를 계속 갱신하고 있는지 여부를 나타냄

    // GPS, NETWORK를 위한 퍼미션
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION)

    lateinit var basedate : String // 현재 날짜 yyyy-mm-dd
    lateinit var basetime : String // 현재 시간 hh:mm:ss

    lateinit var unixToRealDate : String // 유닉스 날짜 -> 실제 날짜
    lateinit var unixToRealTime : String // 유닉스 시간 -> 실제 시간

    private var minTemp : Double = 0.0 // 최저온도
    private var maxTemp : Double = 0.0 // 최고온도


    private var curPoint : Point ?= null // 현재 위치의 격자 좌표를 저장할 포인트

    // [필수] API key
    val API_KEY = "02b85280a70feaf4826a25a741b7e970"

    // 새로고침 버튼 누르면 텍스트가 누적이 됨.
    // 누적되는 텍스트 없애주는 함수
    fun clearLayout() {
        binding.apply {
            humidity.text = "습도: "
            temp.text = "현재 온도: "
            feels.text = "체감온도: "
            low.text = "최저온도: "
            high.text = "최고온도: "
        }
    }

    var isReset = false // Layout에 위치 초기화 버튼 작동시 필요한 isReset 값
    lateinit var countryLat : String // 도시 선택하기에서 선택한 도시의 위도
    lateinit var countryLon : String // 도시 선택하기에서 선택한 도시의 경도

    lateinit var binding : ActivityMainBinding

    private var cold = 1
    private var hot = 1
    private var weather2 = ""
    private var imgSource2 = 1

    private fun buttonListener() {
        // chooseClothes()의 startActivity랑 겹침
//        binding.closet.setOnClickListener {
//            val intent = Intent(this,ClosetActivity::class.java)
//            startActivity(intent)
//        }

        binding.userBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.UserDialog()
        }
        binding.userTxt.setOnClickListener {
            val dialog = Dialog(this)
            dialog.UserDialog()
        }
    }

    // 앱 다시 재개되면 위치 업데이트 켬
    override fun onResume() {
        super.onResume()
        Log.i("cycle", "onResume()")

        var result = intent.getStringExtra("cityNameHandling")?.split(",")
        if (result != null) {
            isReset = true
            countryLat = result[0]
            countryLon = result[1]
            Log.d("cityNameHandling", lat + "," + lon)
            clearLayout()
            setMinAndMaxTemp(countryLat, countryLon)
            setCurrentWeather(countryLat, countryLon)
        }

        if(!startUpdate && !isReset) startLocationUpdates()
    }

    // 앱 중지되면 위치 업데이트 끔
    override fun onPause() {
        super.onPause()

        Log.i("cycle", "onPause()")

        stopLocationUpdate()
    }

    // 앱 실행하면 자동 실행되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLocation() // 가상 에뮬레이터 위치 정보 변경 기능을 사용해서 설정한 위치로 현재 위치를 설정해준다.
        setMinAndMaxTemp(lat, lon) // 최저, 최고 온도 설정
        setCurrentWeather(lat, lon) // URL로 날씨 데이터 요청, 날씨 모델 생성

        // 도시선택하기 버튼 눌렀을 떄
        binding.cityBtn.setOnClickListener {
            var intentCity = Intent(this, CityActivity::class.java) // 도시 목록 Activity Intent 설정
            clearLayout() // 메인화면의 [날씨 정보]를초기화 해준다.
            startActivity(intentCity) // 도시 목록 Activity로 전환한다.
        }

        binding.cityTxt.setOnClickListener {
            var intentCity = Intent(this, CityActivity::class.java) // 도시 목록 Activity Intent 설정
            clearLayout() // 메인화면의 [날씨 정보]를초기화 해준다.
            startActivity(intentCity) // 도시 목록 Activity로 전환한다.
        }

        // 새로고침 버튼 눌렀을 때
        binding.change.setOnClickListener {
            clearLayout() // 메인화면의 [날씨 정보]를초기화 해준다.
            if(!isReset) { // 도시 목록에서 선택한 적이 없다면
                initLocation() // 가상 에뮬레이터 위치 정보 변경 기능을 사용해서 설정한 위치로 현재 위치를 설정해준다.
                setMinAndMaxTemp(lat, lon)  // 최저, 최고 온도 설정
                setCurrentWeather(lat, lon) // URL로 날씨 데이터 요청, 날씨 모델 생성
            } else { // 도시 목록에서 도시 선택한 적이 있다면
                setMinAndMaxTemp(countryLat, countryLon) // 해당 도시 정보로 최저, 최고 온도 설정
                setCurrentWeather(countryLat, countryLon) // 해당 도시 정보로 날씨 데이터 요청, 날씨 모델 생성
            }
        }

        // 도시선택 초기화 버튼 누르면
        // 초기에 설정한 gps정보로 위치설정하기 위해
        binding.resetBtn.setOnClickListener {
            isReset = false
        }
        binding.resetTxt.setOnClickListener {
            isReset = false
        }

        cold = intent.getIntExtra("cold",0)
        hot = intent.getIntExtra("hot",0)

        binding.closet.setOnClickListener {
            chooseClothes(tempForIntent!!, humidityForIntent!!, windSpeedForIntent!!)
        }

        buttonListener()
    }

    // 현재 날씨 설정하는 함수
    private fun setCurrentWeather(lat : String, lon : String) {
        getCurrentTime() // 현재 날짜, 현재 시간 설정함.

        val call = ApiObject.retrofitService.getCurrentWeatherData(lat, lon, API_KEY, "metric", "daily,hourly,minutely,alerts") // 요청

        // 비동기적 요청
        call.enqueue(object : retrofit2.Callback<WEATHERRESPONSE> {
            override fun onResponse(
                call: Call<WEATHERRESPONSE>,
                response: Response<WEATHERRESPONSE>
            ) {
                if (response.isSuccessful) { // 응답이 성공적으로 들어오면
                    Log.d("resoponseLog", "API Successful!!!")

                    var currentModelWeahter = ModelWeather() // 날씨 데이터 저장할 모델 생성

                    var responseBody = response.body()!! // 응답받은 JSON 텍스트 전체 저장하는 변수
                    var weatherList = responseBody.current.weather

                    currentModelWeahter.timezone = responseBody.timezone // [asia/seoul]형태의 지역정보
                    currentModelWeahter.dt = responseBody.current.dt // 유닉스 시간
                    currentModelWeahter.temp = responseBody.current.temp // 현재온도
                    currentModelWeahter.feels_like = responseBody.current.feels_like // 체감온도
                    currentModelWeahter.humidity = responseBody.current.humidity // 습도
                    currentModelWeahter.wind_speed = responseBody.current.wind_speed // 풍속
                    currentModelWeahter.main = weatherList[0].main // 구름, 맑음 등 정보

//                    chooseClothes(currentModelWeahter.temp!!, currentModelWeahter.humidity!!, currentModelWeahter.wind_speed!!)
                    tempForIntent = currentModelWeahter.temp
                    humidityForIntent = currentModelWeahter.humidity
                    windSpeedForIntent = currentModelWeahter.wind_speed

                    var city = findViewById<TextView>(R.id.city)
                    var temp = findViewById<TextView>(R.id.temp)
                    var weather = findViewById<TextView>(R.id.weather)
                    var weatherImg = findViewById<ImageView>(R.id.weatherImg)
                    var humidity = findViewById<TextView>(R.id.humidity)
                    var feels = findViewById<TextView>(R.id.feels)
                    var date = findViewById<TextView>(R.id.date)
                    var time = findViewById<TextView>(R.id.time)
                    var imgSource = setWeatherImg(currentModelWeahter.main.toString())

                    /* --------------------------------------GeoCoder---------------------------------------- */
                    // 설정된 위도, 경도로 도시 이름 불러옴
                    var tempcity = ""
                    var geocoder = Geocoder(this@MainActivity)
                    var cityList = geocoder.getFromLocation(lat.toDouble(), lon.toDouble(), 10)
                    if(cityList != null) {
                        if(cityList.size != 0) {
                            tempcity = cityList.get(0).adminArea
                        } else {
                            Log.d("cityLog", "There is no city Information you selected!")
                        }
                    }
                    /* -------------------------------------------------------------------------------------- */

                    calcUnixDateToRealDate(currentModelWeahter.dt!!) // [변환] 유닉스 -> 실제

                    /* --------------------------------------레이아웃에 데이터 설정한다-------------------------------------------------------------- */
                    weatherImg.setImageResource(imgSource)
                    city.text = tempcity
                    temp.text = temp.text.toString() + Math.round(currentModelWeahter.temp!!).toString()
                    weather.text = translateWeatherLangToKorean(currentModelWeahter.main.toString())
                    humidity.text = humidity.text.toString() + isOptimalHumidity(currentModelWeahter.temp!!, currentModelWeahter.humidity!!)
                    feels.text = feels.text.toString() + Math.round(currentModelWeahter.feels_like!!).toString()
                    date.text = unixToRealDate
                    time.text = unixToRealTime
                    /* ----------------------------------------------------------------------------------------------------------------------- */
                    weather2=weather.text.toString()
                    imgSource2=imgSource.toInt()

                } else { // 응답 성공적으로 들어오지 못하면
                    Log.d("responseLog", "API Failed..!")
                }
            }

            // api 호출 실패
            override fun onFailure(call: Call<WEATHERRESPONSE>, t: Throwable) {
                Log.d("api fail", t.message.toString())
            }
        })
    }

    // 최저, 최고 온도 api 호출함수
    private fun setMinAndMaxTemp(lat : String, lon : String) {
        val call = DailyApiObject.dailyRetrofitService.getDailyWeatherData(lat, lon, API_KEY, "metric", "hourly,current,minutely,alerts") // 요청
        call.enqueue(object : retrofit2.Callback<DAILYRESPONSE> {
            override fun onResponse(
                call: Call<DAILYRESPONSE>,
                response: Response<DAILYRESPONSE>
            ) {
                if (response.isSuccessful) {
                    Log.d("resoponseLog", "API Successful!!!")

                    var responseBody = response.body()!!  // 응답받은 JSON 텍스트 전체 저장하는 변수
                    var daily = responseBody.daily[0]
                    var temp = daily.temp

                    minTemp = temp.min // api 호출로 받아온 최저 온도 설정
                    maxTemp = temp.max // api 호출로 받아온 최고 온도 설정

                    var low = findViewById<TextView>(R.id.low)
                    var high = findViewById<TextView>(R.id.high)

                    /* ------------------------레이아웃에 데이터 설정한다----------------------- */
                    low.text = low.text.toString() + Math.round(minTemp!!).toString()
                    high.text = high.text.toString() + Math.round(maxTemp!!).toString()
                    /* ------------------------------------------------------------------ */

                } else { // 응답 성공적으로 들어오지 못하면
                    Log.d("responseLog", "API Failed..!")
                }
            }

            // api 호출 실패
            override fun onFailure(call: Call<DAILYRESPONSE>, t: Throwable) {
                Log.d("api fail", t.message.toString())
            }
        })
    }

    // 날씨에 따른 이미지 설정하는 함수
    private fun setWeatherImg(desc : String) : Int {
        return when(desc) {
            "Clear" -> R.drawable.sun
            "Clouds" -> R.drawable.cloudy
            "Thunderstorm" -> R.drawable.thunderstorm
            "Rain" -> R.drawable.rainy
            "Snow" -> R.drawable.snowy
            "Mist" -> R.drawable.mist
            "Haze" -> R.drawable.haze
            "Fog" -> R.drawable.fog
            "Sand" -> R.drawable.wind
            "Dust" -> R.drawable.dust
            "Squall" -> R.drawable.squall
            "Tornado" -> R.drawable.tornado
            "Drizzle" -> R.drawable.drizzle
            else -> R.drawable.nonweather
        }
    }

    // [번역함수] 영어 -> 한글
    private fun translateWeatherLangToKorean(main : String) : String {
        return when(main) {
            "Clear" -> "맑음"
            "Clouds" -> "구름 낌"
            "Thunderstorm" -> "천둥번개"
            "Rain" -> "비"
            "Snow" -> "눈"
            "Mist" -> "엷은 안개"
            "Haze" -> "실안개"
            "Fog" -> "안개"
            "Sand" -> "황사"
            "Dust" -> "먼지 심함"
            "Squall" -> "돌풍"
            "Tornado" -> "토네이도"
            "Drizzle" -> "보슬비"
            else -> main
        }
    }

    // Calendar를 호출해서 현재 날짜, 시간을 불러온다.
    private fun getCurrentTime() {
        val date = Calendar.getInstance().time
        basedate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date) // 2022-05-29
        var hour = SimpleDateFormat("HH", Locale.getDefault()).format(date.time) // 15:
        hour = (hour.toInt() + 1).toString() + ":"
        val min = "00:"
        val sec = "00"
        basetime = hour + min + sec // hour + min + sec = 15:12:00
    }

    // [변환 함수] 유닉스 시간 -> 실제 시간
    private fun calcUnixDateToRealDate(dt : Int) {
        val date = Date(dt * 1000L)
        unixToRealDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        val hour = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(date.time)
        unixToRealTime = hour
    }

    // 습도(정수형)에 따라서 건조, 쾌적, 습함을 구별해서 그 값을 리턴
    private fun isOptimalHumidity(temp: Double, humidity: Int) : String {
        var tempToInt = Math.round(temp).toInt()
        var resultMsg : String = ""

        when(tempToInt) {
            in -99..16 -> {
                if(humidity < 80) resultMsg = "건조"
                else if(humidity == 80) resultMsg = "쾌적"
                else resultMsg = "습함"
            }
            17 -> {
                if(humidity < 70) resultMsg = "건조"
                else if(humidity == 70) resultMsg = "쾌적"
                else resultMsg = "습함"
            }
            in 18..20 -> {
                if(humidity < 60) resultMsg = "건조"
                else if(humidity == 60) resultMsg = "쾌적"
                else resultMsg = "습함"
            }
            in 21..23-> {
                if(humidity < 50) resultMsg = "건조"
                else if(humidity == 50) resultMsg = "쾌적"
                else resultMsg = "습함"
            }
            24 -> {
                if(humidity < 40) resultMsg = "건조"
                else if(humidity == 40) resultMsg = "쾌적"
                else resultMsg = "습함"
            }
            in 25..99 -> {
                if(humidity < 30) resultMsg = "건조"
                else if(humidity == 30) resultMsg = "쾌적"
                else resultMsg = "습함"
            }
        }
        return resultMsg
    }

    /* --------------------------------------------------------------- 여기서부터는 GPS에 관한 설정입니다 ------------------------------------------------------------------------- */
    val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(checkGPSProvider()) {
            startLocationUpdates()
        } else { // 허용하지 않았다면 기존의 위치를 현재 위치로 설정
            Toast.makeText(this, "GPSProvider 허용 안했음", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkFineLocationPermission() : Boolean {
        return ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCoarseLocationPermission() : Boolean {
        return ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkGPSProvider() : Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }

    private fun showGPSSetting() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "위치 설정을 허용하시겠습니까?"
        )
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
            val GpsSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activityResultLauncher.launch(GpsSettingIntent)
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            Toast.makeText(this, "위치 설정 허용 취소버튼 누름", Toast.LENGTH_SHORT).show()
        })

        builder.create().show()
    }

    fun initLocation() {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            locationRequest = LocationRequest.create().apply {
                interval = 10000 // 10초
                fastestInterval = 3000 // 3초
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            locationRequest2 = LocationRequest.create().apply {
                interval = 10000 // 10분
                fastestInterval = 3000 // 3분
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(location: LocationResult) {
                    if (location.locations.size == 0) return
                    loc = LatLng(location.locations[location.locations.size - 1].latitude,
                        location.locations[location.locations.size - 1].longitude)
                    lat = loc.latitude.toString()
                    lon = loc.longitude.toString()
                    Log.i("location", "LocationCallback()")
                }
            }
        getLastLocation()
    }

    // 위치 업데이트 함수
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        when {
            checkFineLocationPermission() -> {
                if(!checkGPSProvider()) {
                    showGPSSetting()
                } else {
                    startUpdate = true
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest, locationCallback, Looper.getMainLooper()
                    )
                    Log.i("location", "startLocationUpdates()")
                }
            }

            checkCoarseLocationPermission() -> { // Not using GPS, but using Network
                startUpdate = true
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest2, locationCallback, Looper.getMainLooper()
                )
                Log.i("location2", "startLocationUpdates()")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                showPermissionReqeustDlg()
            }

            else -> {
                locationPermissionRequest.launch(permissions)
            }
        }
    }

    // 위치 업데이트 중지 함수
    private fun stopLocationUpdate() { // 더 이상 콜백함수를 호출해서 위치 정보 갱신하는 것 안하겠다
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startUpdate = false
        Log.i("location", "stopLocationUpdates()")
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        when {
            checkFineLocationPermission() -> { // 정확한 위치 정보 -> GPS
                if(!checkGPSProvider()) {
                    showGPSSetting()
                } else {
                    fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                        object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                                return CancellationTokenSource().token
                            }

                            override fun isCancellationRequested(): Boolean {
                                return false
                            }
                        }).addOnSuccessListener { // 성공적으로 위치 정보 들어오면

                        if(it != null) {
                            loc = LatLng(it.latitude, it.longitude)
                        }
                        lat = loc.latitude.toString()
                        lon = loc.longitude.toString()
                    }
                }
            }

            checkCoarseLocationPermission() -> {
                fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                            return CancellationTokenSource().token
                        }

                        override fun isCancellationRequested(): Boolean {
                            return false
                        }
                    }).addOnSuccessListener {
                    if(it != null) {
                        loc = LatLng(it.latitude, it.longitude)
                    }
                    lat = loc.latitude.toString()
                    lon = loc.longitude.toString()
                }
            }

            // 명시적으로 권한 거부
            // 다시 한번 요청
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                showPermissionReqeustDlg()
            }
            else -> {
                locationPermissionRequest.launch(permissions)
            }
        }
    }

    private fun showPermissionReqeustDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 제공")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "기기의 위치를 제공하도록 설정하시겠습니까?"
        )
        builder.setPositiveButton("설정", DialogInterface.OnClickListener{ dialog, id ->
            locationPermissionRequest.launch(permissions)
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener{ dialog, id ->
            dialog.dismiss()
            Toast.makeText(this, "위치 설정 허용 취소버튼 누름", Toast.LENGTH_SHORT).show()
        })
        builder.create().show()
    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                startLocationUpdates()
            }
            else -> {
                Toast.makeText(this, "권한 허용되지 않음", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun chooseClothes(temp: Double, humidity: Int, wind: Double) {

        var humidity_r = when {
            humidity < 50 -> 1
            humidity < 75 -> 2
            else -> 3
        }

        var wind_r = if (wind < 20) 1 else 2

        var temp_r = 0.0
        when {
            temp > 20 -> // summer
                temp_r = temp + humidity_r * 3 + hot * 2
            temp < 5 -> // winter
                temp_r = temp - wind_r * 4 - cold * 2
            else -> // spring or fall
                temp_r = temp
        }

        var topIndex = 0
        var bottomIndex = 0
        var listTop = listOf(0)
        var listBottom = listOf(0)
        when {
            temp_r > 30 -> {
                listTop = listOf(0, 2) // 반팔, 민소매
                topIndex = listTop.random()
                bottomIndex = 4 // 반바지
            }
            temp_r > 15 -> {
                listTop = listOf(1, 3, 4) // 긴팔, 셔츠, 카라티
                topIndex = listTop.random()
                listBottom = listOf(0, 1, 2, 3) // 반바지 제외 (데님, 코튼, 슬렉스, 조거팬츠)
                bottomIndex = listBottom.random()
            }
            temp_r <= 15 -> {
                listTop = listOf(5, 6, 7) // 맨투맨, 후드티, 니트
                topIndex = listTop.random()
                listBottom = listOf(0, 1, 2, 3) // 반바지 제외 (데님, 코튼, 슬렉스, 조거팬츠)
                bottomIndex = listBottom.random()
            }
        }

        // topIndex, bottomIndex 전달
        val intent = Intent(this, ClosetActivity::class.java)
        intent.putExtra("topIndex", topIndex)
        intent.putExtra("bottomIndex", bottomIndex)
        intent.putExtra("weather", weather2)
        intent.putExtra("imgSource", imgSource2)
        startActivity(intent)
    }
    /* ---------------------------------------------------------- 여기까지 -----------------------------------------------------------------------*/
}