package com.heavywater.template.feature.currentweather.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heavywater.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _currentCity = MutableStateFlow<String?>(null)
    val currentCity: StateFlow<String?> = _currentCity.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchCurrentCity() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _currentCity.value = locationRepository.getCurrentLocation()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
