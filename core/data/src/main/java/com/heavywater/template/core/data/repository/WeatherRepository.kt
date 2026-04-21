package com.heavywater.template.core.data.repository

import com.heavywater.template.core.model.WeatherInfo

interface WeatherRepository {
    suspend fun getCurrentWeather(query: String): Result<WeatherInfo>
}
