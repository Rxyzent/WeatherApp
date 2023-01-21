package com.rxyzent.weatherapp.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConnection {
    companion object {

        private fun getRetrofit():Retrofit{
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        fun getApiMethods():ApiMethods{
            return getRetrofit().create(ApiMethods::class.java)
        }
    }
}