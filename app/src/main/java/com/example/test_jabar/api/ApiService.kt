package com.example.test_jabar.api

import com.example.test_jabar.data.response.CurrentWeatherResponse
import com.example.test_jabar.data.response.Forecast5Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
//    @POST("/api/user/login")
//    @FormUrlEncoded
//    fun login(@Field("email") email: String,
//                       @Field("password") password: String,
//                       @Field("deviceId") deviceId: String): Observable<WrapperResponse>
    @GET("data/2.5/forecast")
    fun getForecast5(
        @Query("zip") zip:String,
        @Query("appid") idApp:String,
        @Query("units") unit:String
    ):Observable<Forecast5Response>

    @GET("data/2.5/weather")
    fun getWeatherNow(
        @Query("zip") zip:String,
        @Query("appid") idApp:String,
        @Query("units") unit:String
    ):Observable<CurrentWeatherResponse>
}