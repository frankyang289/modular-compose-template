package com.heavywater.template.feature.currentweather.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heavywater.template.core.data.repository.LocationRepository
import com.heavywater.template.core.data.repository.WeatherInfo
import com.heavywater.template.core.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _currentCity = MutableStateFlow<String?>(null)
    val currentCity: StateFlow<String?> = _currentCity.asStateFlow()

    private val _weather = MutableStateFlow<WeatherInfo?>(null)
    val weather: StateFlow<WeatherInfo?> = _weather.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchCurrentCity() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val city = locationRepository.getCurrentLocation()
                _currentCity.value = city
                if (city != null) {
                    fetchWeather(city)
                }
            } catch (e: Exception) {
                _error.value = "Could not determine location."
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchWeather(query: String) {
        try {
            _weather.value = weatherRepository.getCurrentWeather(query)
        } catch (e: Exception) {
            _error.value = "Weather unavailable: ${e.message}"
        }
    }
}
