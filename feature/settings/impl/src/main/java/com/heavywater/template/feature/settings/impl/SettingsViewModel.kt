package com.heavywater.template.feature.settings.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heavywater.template.core.model.DarkThemeConfig
import com.heavywater.template.core.model.UserData
import com.heavywater.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    val settingsUiState: StateFlow<SettingsUiState> =
        userDataRepository.userData
            .map { SettingsUiState.Success(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                SettingsUiState.Loading,
            )

    fun updateDarkThemeConfig(config: DarkThemeConfig) {
        viewModelScope.launch { userDataRepository.setDarkThemeConfig(config) }
    }

    fun updateDynamicColor(useDynamicColor: Boolean) {
        viewModelScope.launch { userDataRepository.setDynamicColorPreference(useDynamicColor) }
    }
}

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val userData: UserData) : SettingsUiState
}