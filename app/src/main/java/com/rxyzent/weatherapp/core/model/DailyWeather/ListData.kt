package com.rxyzent.weatherapp.core.model.DailyWeather

data class ListData (
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Int,
    val sys: Sys,
    val dt_txt: String
)