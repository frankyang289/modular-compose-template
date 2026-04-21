package com.heavywater.template.core.model

data class WeatherInfo(
    val cityName: String,
    val country: String,
    val tempCelsius: Double,
    val feelsLikeCelsius: Double,
    val conditionText: String,
    /** Full HTTPS URL ready to pass straight to an image loader. */
    val conditionIconUrl: String,
    val humidity: Int,
    val windKph: Double,
    val isDay: Boolean,
)
