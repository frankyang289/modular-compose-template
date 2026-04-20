package com.heavywater.template.core.data.repository

/** Domain model — only the fields the UI actually needs. */
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

interface WeatherRepository {
    /**
     * Fetch current weather for [query].
     * [query] can be a city name, "lat,lon" pair, zip code, or IP address.
     *
     * Throws on network errors — callers should wrap in a try/catch or
     * use runCatching if they want to surface errors to the UI gracefully.
     */
    suspend fun getCurrentWeather(query: String): WeatherInfo
}
