package com.example.openweathermapapi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.openweathermapapi.databinding.ActivityClosetBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class ClosetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClosetBinding

    var topIndex : Int = 0
    var bottomIndex : Int = 0

    val scope = CoroutineScope(Dispatchers.Main)

    private var weather = ""
    private var imgSource = 1

    //    https://www.musinsa.com/category/001002
//    https://www.musinsa.com/category/001003?d_cat_cd=&brand=&rate=&page_kind=search&list_kind=small&sort=pop&sub_sort=&page=1
    val urlA = "https://www.musinsa.com/category/"
    val urlB = "?d_cat_cd="
    val urlC = "&brand=&rate=&page_kind=search&list_kind=small&sort=pop&sub_sort=&page=1"

    var bitmap: Bitmap? = null
    var bitmap2: Bitmap? = null
    var imageSrc = "" // 상의
    var imageSrc2 = "" // 하의
    var imageList = arrayListOf<String>() // 특정 상의 종류를 저장하는 리스트
    var imageList2 = arrayListOf<String>() // 특정 하의 종류를 저장하는 리스트

    var URLarr = arrayListOf("001001",
        "001010",
        "001011",
        "001002",
        "001003",
        "001005",
        "001004",
        "001006") // 상의 종류
    // 001001 : 반팔 티셔츠,
    // 001010 : 긴팔 티셔츠,
    // 001011 : 민소매,
    // 001002 : 셔츠,
    // 001003 : 카라티,
    // 001005 : 맨투맨,
    // 001004 : 후드티,
    // 001006 : 니트

    var URLarr2 = arrayListOf("003002",
        "003007",
        "003008",
        "003004",
        "003009",
        "003005",
        "003010",
        "003006") // 하의 종류
    // 003002 : 데님 팬츠,
    // 003007 : 코튼 팬츠,
    // 003008 : 슬랙스,
    // 003004 : 조거 팬츠,
    // 003009 : 반바지,
    // 003005 : 레깅스,
    // 003010 : 점프 슈트

    var URLarr_size = URLarr.size // [상의 종류 리스트]의 크기
    var URLarr2_size = URLarr2.size // [하의 종류 리스트]의 크기
    var imageListList = arrayListOf<ArrayList<String>>() // 상의
    var imageListList2 = arrayListOf<ArrayList<String>>() // 하의

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClosetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonListener() // 버튼 클릭했을 때 다른 Activity로 넘어가는 함수
        topIndex = intent.getIntExtra("topIndex", 0)
        bottomIndex = intent.getIntExtra("bottomIndex", 0)
        getTop() // 상의
        getPants() // 하의

        weather = intent.getStringExtra("weather")!!
        imgSource = intent.getIntExtra("imgSource", 0)
        var weather2 = findViewById<TextView>(R.id.weather)
        weather2.text = weather2.text.toString() + weather
        var weatherImg = findViewById<ImageView>(R.id.weatherImg)
        weatherImg.setImageResource(imgSource)


    }



    // 다른 액티비티 버튼 제어 함수
    private fun buttonListener() {
        binding.home.setOnClickListener { // home 버튼 눌렀을 때 액티비티 전환
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // 상의 이미지 저장해서 불러오기
    private fun getTop() {
        val top : Int = 0
        val bottom : Int = 0

        val toptxt = findViewById<TextView>(R.id.topTxt) // Text :[다른 상의 추천받기]
        val topbtn = findViewById<ImageView>(R.id.topBtn)

        scope.launch {
            withContext(Dispatchers.IO) {
                for (j in 0..URLarr_size - 1) { // 2차원 배열에 넣기
                    var count = 0 // 특정 종류마다 옷 몇개 받아올지 카운트 하는 변수

                    val url = urlA + URLarr[j] + urlB + urlC // URL 생성
                    val doc = Jsoup.connect(url).get() // Document
                    val imageset =
                        doc.select("div.li_inner>div.list_img>a.img-block>img") // 상의 이미지에 해당하는 태그(?)정보 갖고 있는 ImageSet

                    for (image in imageset) { // 이미지 하나마다
                        if (count == 10) break // 옷 이미지 10개 다 채우면 종료
                        count++
                        imageSrc = image.attr("data-original")
                        imageList.add(imageSrc)
                    }
                    imageListList.add(j, imageList)
                    imageListList[j].shuffle() // 랜덤하게 순서를 섞어줌
                }
                showImage(imageListList[topIndex][0]) // 일단 이미지 한개 먼저 보여줌
            }
        }

        // [다른 상의 추천받기] 텍스트 클릭시
        toptxt.setOnClickListener {
            // 이 부분도 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해야 한다.
            // 현재 인덱스 : 0은 작동확인용 임시 인덱스임
            imageListList[topIndex].shuffle()

            // imageListList의 첫번째 인덱스는 옷 종류를 나타냄 (URLarr에 옷 종류 있음)
            // 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해서 showImage(...) 해야 함
            showImage(imageListList[topIndex][0])
        }


        topbtn.setOnClickListener {
            // 이 부분도 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해야 한다.
            // 현재 인덱스 : 0은 작동확인용 임시 인덱스임
            imageListList[topIndex].shuffle()

            // imageListList의 첫번째 인덱스는 옷 종류를 나타냄 (URLarr에 옷 종류 있음)
            // 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해서 showImage(...) 해야 함
            showImage(imageListList[topIndex][0])
        }
    }




    // 하의 이미지 저장해서 불러오기
    private fun getPants() {
        val bottomtxt = findViewById<TextView>(R.id.bottomTxt) // Text : [다른 하의 추천받기]
        val bottombtn = findViewById<ImageView>(R.id.bottomBtn)
        scope.launch {
            withContext(Dispatchers.IO) {
                for (j in 0..URLarr2_size - 1) { // 2차원 배열에 넣기
                    var count = 0 // 특정 종류마다 옷 몇개 받아올지 카운트 하는 변수

                    val url = urlA + URLarr2[j] + urlB + urlC // URL 생성
                    val doc = Jsoup.connect(url).get() // Document
                    val imageset =
                        doc.select("div.li_inner>div.list_img>a.img-block>img") // 하의 이미지에 해당하는 태그(?)정보 갖고 있는 ImageSet

                    for (image in imageset) { // 이미지 하나마다
                        if(count == 10) break // 옷 이미지 10개 다 채우면 종료
                        count++
                        imageSrc2 = image.attr("data-original")
                        imageList2.add(imageSrc2)
                    }

                    imageListList2.add(j, imageList2)
                    imageListList2[j].shuffle() // 랜덤하게 순서를 섞어줌

                }
                showImage2(imageListList2[bottomIndex][0]) // 일단 이미지 한개 보여줌
            }

            // [다른 하의 추천받기] 텍스트 클릭시
            bottomtxt.setOnClickListener {
                // 이 부분도 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해야 한다.
                // 현재 인덱스 : 2는 작동확인용 임시 인덱스임
                // val bottomIndex = intent.getIntExtra("bottom", bottom)
                imageListList2[bottomIndex].shuffle()

                // imageListList2의 첫번째 인덱스는 옷 종류를 나타냄 (URLarr2에 옷 종류 있음)
                // 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해서 showImage2(...) 해야 함
                showImage2(imageListList2[bottomIndex][0])
            }
            bottombtn.setOnClickListener {
                // 이 부분도 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해야 한다.
                // 현재 인덱스 : 2는 작동확인용 임시 인덱스임
                imageListList2[bottomIndex].shuffle()

                // imageListList2의 첫번째 인덱스는 옷 종류를 나타냄 (URLarr2에 옷 종류 있음)
                // 소민님 계산함수 리턴값 받아와서 그 리턴값에 맞는 인덱스를 설정해서 showImage2(...) 해야 함
                showImage2(imageListList2[bottomIndex][0])
            }
        }

    }

    // 실제 상의 이미지 보여주는 함수
    private fun showImage(imageURL: String) {
        scope.launch {
            withContext(Dispatchers.IO) {
                bitmap = loadImage(imageURL) // Bitmap 변환
            }
            binding.top.setImageBitmap(bitmap)
            // 이미지 보여주기
        }
    }

    // 실제 하의 이미지 보여주는 함수
    private fun showImage2(imageURL: String) {
        scope.launch {
            withContext(Dispatchers.IO) {
                bitmap2 = loadImage2(imageURL) // Bitmap 변환
            }
            binding.bottom.setImageBitmap(bitmap2)
            // 이미지 보여주기
        }
    }

    // 상의 이미지 불러오기
    private fun loadImage(imageURL: String): Bitmap? {
        val bmp: Bitmap? = null
        try {
            val url = URL(imageURL)
            val stream = url.openStream()

            return BitmapFactory.decodeStream(stream)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmp
    }

    // 하의 이미지 불러오기
    private fun loadImage2(imageURL2: String): Bitmap? {
        val bmp: Bitmap? = null
        try {
            val url = URL(imageURL2)
            val stream = url.openStream()

            return BitmapFactory.decodeStream(stream)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmp
    }
}