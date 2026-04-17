package com.heavywater.template.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.heavywater.template.feature.currentweather.api.CurrentWeatherNavKey
import com.heavywater.core.designsystem.icon.AppIcons
import com.heavywater.template.feature.currentweather.api.R as currentWeatherR


data class BottomNavigationItem(
    @StringRes val labelTextId: Int? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
)

val CURRENT_WEATHER = BottomNavigationItem(
    currentWeatherR.string.feature_currentweather_api_title,
    AppIcons.currentWeather,
    AppIcons.currentWeatherBorder

)

val BOTTOM_NAV_ITEMS = mapOf(
    CurrentWeatherNavKey to CURRENT_WEATHER
)