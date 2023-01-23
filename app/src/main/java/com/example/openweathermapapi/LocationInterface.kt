/* 안씁니다. 혹시 몰라서 남겨둔 인터페이스 */
//package com.example.openweathermapapi
//
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface LocationInterface {
//    @GET("reverse?")
//    fun getLocationData(
//        @Query("lat") lat : String,
//        @Query("lon") lon : String,
//        @Query("limit") limit : String,
//        @Query("appid") appid : String
//    ): Call<LOCATIONRESPONSE>
//}
//
//data class LOCATIONRESPONSE(var local_names : LOCAL_NAMES)
//data class LOCAL_NAMES(var ko : String)
//
//val locationRetrofit = Retrofit.Builder()
//    .baseUrl("http://api.openweathermap.org/geo/1.0/")
//    .addConverterFactory(GsonConverterFactory.create())
//    .build()
//
//object LocationAPIObject {
//    val LocationRetrofitService: LocationInterface by lazy {
//        locationRetrofit.create(LocationInterface::class.java)
//    }
//}