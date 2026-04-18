package com.heavywater.data.repository

import com.heavywater.template.core.model.DarkThemeConfig
import com.heavywater.template.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}