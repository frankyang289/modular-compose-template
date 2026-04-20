package com.heavywater.template.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    /**
     * Fetch current weather conditions for any location.
     *
     * @param apiKey  Your WeatherAPI key — get one free at https://www.weatherapi.com/
     * @param query   City name, lat/lon, zip code, or IP address (e.g. "London", "48.8567,2.3508")
     * @param aqi     Pass "yes" to include air-quality data in the response
     *
     * Example URL:
     *   GET http://api.weatherapi.com/v1/current.json?key=YOUR_KEY&q=London&aqi=no
     */
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("aqi") aqi: String = "no",
    ): CurrentWeatherResponse
}

@Serializable
data class CurrentWeatherResponse(
    val location: LocationDto,
    val current: CurrentDto,
)

@Serializable
data class LocationDto(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerialName("tz_id") val timezoneId: String,
    @SerialName("localtime") val localTime: String,
)

@Serializable
data class CurrentDto(
    @SerialName("temp_c") val tempCelsius: Double,
    @SerialName("temp_f") val tempFahrenheit: Double,
    @SerialName("feelslike_c") val feelsLikeCelsius: Double,
    @SerialName("feelslike_f") val feelsLikeFahrenheit: Double,
    @SerialName("is_day") val isDay: Int, // 1 = day, 0 = night
    val condition: ConditionDto,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("wind_dir") val windDirection: String,
    val humidity: Int,
    @SerialName("vis_km") val visibilityKm: Double,
    @SerialName("uv") val uvIndex: Double,
)

@Serializable
data class ConditionDto(
    val text: String, // e.g. "Partly cloudy"
    val icon: String, // URL fragment, prefix with "https:" to load
    val code: Int,
)
