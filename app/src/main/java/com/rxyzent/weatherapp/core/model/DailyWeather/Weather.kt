package com.rxyzent.weatherapp.core.model.DailyWeather

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)