package com.rxyzent.weatherapp.core.model.DailyWeather

data class DailyWeatherData(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ListData>,
    val city: City
)