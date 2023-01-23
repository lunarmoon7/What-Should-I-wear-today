package com.example.openweathermapapi

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
}
// 아래의 data class는 응답받은 JSON 타입의 파일을 분석해서 정의한 클래스입니다
/* DO NOT TOUCH! */
data class WEATHERRESPONSE(var current : CURRENT, var timezone : String, var lat : Double, var lon : Double)
data class CURRENT(var dt : Int, var temp : Double, var feels_like : Double, var humidity : Int, var wind_speed : Double, var weather : List<WEATHER>)
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
