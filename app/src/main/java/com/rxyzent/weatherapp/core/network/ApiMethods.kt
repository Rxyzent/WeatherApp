package com.rxyzent.weatherapp.core.network

import com.rxyzent.weatherapp.core.model.CurrentWeather.CurrentWeatherData
import com.rxyzent.weatherapp.core.model.DailyWeather.DailyWeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMethods {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") key:String,
        @Query("units") units:String
    ):Call<CurrentWeatherData>
    @GET("data/2.5/forecast")
    fun getDailyWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") key:String,
        @Query("units") units:String
    ):Call<DailyWeatherData>
    @GET("img/wn/{idStr}@2x.png")
    fun getImage(
    @Path("idStr") idStr:String
    ):Call<Int>
}