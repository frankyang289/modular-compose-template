package com.heavywater.template.core.datastore

import androidx.datastore.core.DataStore
import com.heavywater.template.core.model.DarkThemeConfig
import com.heavywater.template.core.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class YourApplicationPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map { prefs ->
            UserData(
                darkThemeConfig = when (prefs.dark_theme_config) {
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                        DarkThemeConfig.LIGHT

                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK ->
                        DarkThemeConfig.DARK

                    else ->
                        DarkThemeConfig.FOLLOW_SYSTEM
                },
                useDynamicColor = prefs.use_dynamic_color,
            )
        }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData { prefs ->
            prefs.copy(
                dark_theme_config = when (darkThemeConfig) {
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                    DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                }
            )
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData { prefs ->
            prefs.copy(use_dynamic_color = useDynamicColor)
        }
    }
}