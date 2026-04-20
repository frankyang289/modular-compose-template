package com.heavywater.template.core.data.repository

import com.heavywater.template.core.data.BuildConfig
import com.heavywater.template.core.network.WeatherApiService
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
) : WeatherRepository {

    override suspend fun getCurrentWeather(query: String): WeatherInfo {
        val response = weatherApiService.getCurrentWeather(
            apiKey = API_KEY,
            query = query,
        )
        return WeatherInfo(
            cityName = response.location.name,
            country = response.location.country,
            tempCelsius = response.current.tempCelsius,
            feelsLikeCelsius = response.current.feelsLikeCelsius,
            conditionText = response.current.condition.text,
            conditionIconUrl = "https:${response.current.condition.icon}",
            humidity = response.current.humidity,
            windKph = response.current.windKph,
            isDay = response.current.isDay == 1,
        )
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}
