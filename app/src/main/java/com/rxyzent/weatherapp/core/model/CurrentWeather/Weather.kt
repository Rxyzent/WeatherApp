package com.rxyzent.weatherapp.core.model.CurrentWeather

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)