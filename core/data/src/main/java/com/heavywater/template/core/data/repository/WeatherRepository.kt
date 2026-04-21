package com.heavywater.template.core.data.repository

import com.heavywater.template.core.model.WeatherInfo

interface WeatherRepository {
    /**
     * Fetch current weather for [query].
     * [query] can be a city name, "lat,lon" pair, zip code, or IP address.
     *
     * Returns [Result.success] with [WeatherInfo] on success, or
     * [Result.failure] wrapping the underlying exception on any network or
     * HTTP error -- no need for callers to wrap in try/catch.
     */
    suspend fun getCurrentWeather(query: String): Result<WeatherInfo>
}
