package com.example.openweathermapapi

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyWeatherInterface {
    // API 호출에 필요한 정보를 쿼리로 설정
    /* DO NOT TOUCH! */
    @GET("onecall?")
    fun getDailyWeatherData(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
        @Query("appid") appid : String,
        @Query("units") units : String,
        @Query("exclude") exclude : String
    ): Call<DAILYRESPONSE>
}

data class DAILYRESPONSE(var daily : List<DAILY>)
data class DAILY(var dt : Int, var temp : TEMP)
data class TEMP(var min : Double, var max : Double)


/* 레트로핏 설정 */
val dailyRetrofit = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object DailyApiObject {
    val dailyRetrofitService: DailyWeatherInterface by lazy {
        dailyRetrofit.create(DailyWeatherInterface::class.java)
    }
}