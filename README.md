# 2022-3학년 1학기 모바일 팀 프로그래밍 팀프로젝트
## Overall Description
### 프로젝트 명: 오늘 뭐 입지?
---
### Development Env
---
IDE : Android Studio
Language : Kotlin
API : OpenWeatherMap API

### 팀원
- 문휘식(컴퓨터공학부): API를 사용한 실시간 날씨 정보 & 도시(위치) 정보, 이미지 크롤링 제외 전반적인 기능 설계
- 임하늘(컴퓨터공학부): 전체적인 어플의 UI 디자인
- 이승복(컴퓨터공학부): 옷 추천에 필요한 옷 이미지들 크롤링
- 김소민(스마트ICT융합공학과): 옷 추천 알고리즘 설계
### 주제 : 날씨에 따른 옷(코디) 추천
### 주제 선정 이유
- 급변하는 날씨
- 기존에 존재하는 온도에 따른 옷 차림 추천은 범주가 모호함
- 현 시장에 존재하는 어플들은 기온에만 한정해서 옷을 추천해줌
➡ 이를 보완하기 위함!
### 현 시장에 존재하는 다른 어플 조사
***(하루날씨 - 미세먼지, 날씨, 기상청 / 코디날씨 / Weather Fit)***
> - **긍정적 측면**
>   - 단순 수치상의 기상 정보로는 바깥 날씨 상황을 확실하게 짐작할 수 없었지만 대략의 옷차림 추천만으로도 날씨를 직접적으로 체감할 수 있었다.
>   - 다양하게 여러가지 옷을 매칭해줘서 좋았다.
>   - 외출하기 전 어떤 옷을 입어야 할지에 대한 고민을 줄여준다.
> - **부정적 측면**
>   - 비가 오는지 혹은 바람이 부는지는 고려하지 않고  
오직 기온만으로 옷을 추천해주기 때문에 정확성이 떨어졌다.
>   - 날씨를 설정하는 데 있어서 지역을 구체적으로 설정할 수가 없다.
>   - 동일한 옷이라도 계절마다 모두 특성이 다른데 이를 고려하지 않고 옷을 추천해준다. (봄 블라우스, 겨울 블라우스, 짧은 원피스, 긴 원피스 등)

### 기능 설명
#### 한눈에 보는 기상정보
> 현재 위치의 현재 시간의 날씨 정보를 불러온다.
> 
> 어플리케이션을 실행할 때 마다 혹은 새로 고침 할 때 마다 기상 정보를 갱신한다.
> 
> 각 날씨 상황에 따라 날씨에 맞는 이미지를 보여줌으로서 보다 빠르고 직관적인 날씨 파악이 가능하도록 한다.
#### 체감온도 측정
> API로 받아온 날씨 정보와 입력 받은 사용자 정보를 토대로 사용자가 실제로 체감하는 기온을 계산한다.
> 
> 여러 조건을 고려하여 계절별로 체감온도를 계산한다.
#### 코디 추천
> 기상 정보 안에 포함되는 여러 요인들과 사용자 정보를 참고하여 사용자에게 날씨에 맞는 구체적인 코디를 추천해준다.
>
> 사용자가 해당 코디를 원하지 않을 경우 버튼을 눌러서 조건에 맞는 다른 코디를 추천해준다.
#### 위치 선택
> GPS 권한을 이용해서 사용자(가상 에뮬레이터)의 위치 정보를 변경할 수 있게 한다.
#### 위치 초기화
> 도시 선택 기능으로 도시를 변경했을 때 현재 위치(GPS 이용)로 도시를 바꿀 수 있게 해준다.
#### 도시 선택
> 사용자는 본인이 현재 거주하고 있는 도시를 선택하거나 도시 목록에서 원하는 도시를 선택해서 해당 도시의 날씨 정보를 받아올 수 있다.
#### ~~나만의 옷장~~
> ~~사용자는 실제로 가지고 있는 옷을 추가하고 이 데이터를 기반으로 추천을 받습니다. 만약에 새로운 옷을 구입했다면  나만의 디지털 옷장을 업데이트할 수 있습니다.~~

➡ 모든 팀원이 안드로이드 프로그래밍을 처음 접하다보니 정해진 기간 안에 수행할 수 없다고 판단해서 삭제 함
#### ~~여행지 선택~~
> ~~사용자는 여행 일정(기간, 장소 등)을 입력할 수 있습니다. 여행 목적지에 대한 정확한 기상 대비가 가능합니다. 또한 최적의 날씨를 선별해주는 기능은  여행 계획 수립의 도우미 역할을 합니다.~~
> 
➡ 프로젝트 진행 일정과 맞지 않아 삭제 함
#### ~~기상 특보 알림~~
> ~~정규 예보 외에 이상기후와 천재지변에 대응하기 위해 지역별로 재해 발생의 위험도를 단계별로 제공합니다. 사용자가 위치한 지역 관련 정보는 특별히 알림 팝업을 띄워서 경각심을 갖게 합니다.~~ 
> 
➡ 프로젝트 진행 일정과 맞지 않아 삭제 함

### Screenshots
---
#### 새로고침 할 때 마다 업데이트 되는 실시간 기상 정보

#### 사용자가 선택한 위치로 기상 정보 업데이트

#### 직접 입력 받는 사용자 맞춤 정보

#### 터치 한번으로 간단하게 옷장 페이지로 이동

#### 옷장 페이지에서 확인할 수 있는 날씨 요약정보 & 입력 받은 사용자 정보와 기상 정보를 토대로 옷 추천

#### 추천 받은 옷이 마음에 들지 않을 때 다른 옷 추천받기


## Code Description

### MainActivity.kt

#### API 호출에 필요한 변수, GPS에 필요한 변수 등 선언부
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
    
    var isReset = false // Layout에 위치 초기화 버튼 작동시 필요한 isReset 값
    lateinit var countryLat : String // 도시 선택하기에서 선택한 도시의 위도
    lateinit var countryLon : String // 도시 선택하기에서 선택한 도시의 경도
---
#### clearLayout()
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
---
#### chooseClothes()
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
---
#### setCurrentWeather(...)
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
---
#### setMinAndMaxTemp(...)

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
---
#### setWeatherImg()

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
---
#### translateWeatherLangToKorean(...)
#### getCurrentTime()
#### calcUnixDateToRealDate()
#### isOptimalHumidity()
#### GPS 관한 설정 

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
---
### CityAcitivity.kt
#### 도시 목록 선언

    val countries = arrayOf<String>(
        "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia",
        "Australia", "Austria", "Azerbaijan", "Bahamas, The", "Bahrain", "Bangladesh", "Barbados", "Belarus",
        "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil",
        "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",
        "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros",
        "Congo, Democratic Republic of the", "Congo, Republic of the", "Costa Rica", "Croatia", "Cuba", "Cyprus",
        "Czech Republic", "Denmark, Kingdom of", "Djibouti", "Dominica", "Dominican Republic", "East Timor",
        "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia",
        "Fiji", "Finland", "France", "Gabon", "Gambia, The", "Georgia", "Germany", "Ghana", "Greece", "Grenada",
        "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India",
        "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan",
        "Kazakhstan", "Kenya", "Kiribati", "Korea, North", "Korea, South", "Kuwait", "Kyrgyzstan", "Laos",
        "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
        "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania",
        "Mauritius", "Mexico", "Micronesia, Federated States of", "Moldova", "Monaco", "Mongolia", "Montenegro",
        "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands, Kingdom of the",
        "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau",
        "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal",
        "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",
        "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan",
        "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania",
        "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu",
        "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan",
        "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"
    )
---
#### getLatAndLng()

    private fun getLatAndLng(country : String) : String{
            var geocoder = Geocoder(this@CityActivity)
            var cityList = geocoder.getFromLocationName(country, 1)
            if(cityList != null) {
                if(cityList.size != 0) {
                    var addr = cityList.get(0)
                    return addr.latitude.toString() + "," + addr.longitude.toString()
                }
            }
        return ""
    }
---
### WeatherInterface.kt

    interface WeatherInterface {
    // API 호출에 필요한 정보를 쿼리로 설정
    /* DO NOT TOUCH! */
        @GET("onecall?")
        fun getCurrentWeatherData(
            @Query("lat") lat : String,
            @Query("lon") lon : String,
            @Query("appid") appid : String,
            @Query("units") units : String,
            @Query("exclude") exclude : String
        )
        : Call<WEATHERRESPONSE>
    } // 아래의 data class는 응답받은 JSON 타입의 파일을 분석해서 정의한 클래스입니다
    /* DO NOT TOUCH! */
    
    data class WEATHERRESPONSE(var current : CURRENT, var timezone : String, var lat : Double, var lon : Double)
    data class CURRENT(var dt : Int, var
    temp : Double, var feels_like :
    Double, var humidity : Int, var
    wind_speed : Double, var weather :
    List<WEATHER>)
    data class WEATHER(var main : String, var description : String, var icon : String)
    
    /* 레트로핏 설정 */
    val retrofit = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    
    object ApiObject {
	    val retrofitService: WeatherInterface by lazy {
	        retrofit.create(WeatherInterface::class.java)
	    }
    }
---
### ModelWeather.kt

    class ModelWeather {
	    var timezone : String ?= null
	    var dt : Int ?= null
	    var temp : Double ?= null
	    var feels_like : Double ?= null
	    var humidity : Int ?= null
	    var wind_speed : Double ?= null
	    var main : String ?= null
    }
---
