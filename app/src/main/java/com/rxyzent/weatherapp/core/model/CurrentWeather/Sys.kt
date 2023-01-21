package com.rxyzent.weatherapp.core.model.CurrentWeather

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)