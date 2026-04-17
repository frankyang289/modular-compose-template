package com.heavywater.template.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.heavywater.template.feature.currentweather.api.CurrentWeatherNavKey
import com.heavywater.template.feature.currentweather.impl.CurrentWeatherScreen
import com.heavywater.template.navigation.BOTTOM_NAV_ITEMS
import com.heavywater.core.designsystem.component.AppNavigationBar
import com.heavywater.core.designsystem.component.AppNavigationBarItem

@Composable
fun TemplateApp() {
    TemplateApp(modifier = Modifier)
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3AdaptiveApi::class,
)
internal fun TemplateApp(
    modifier: Modifier = Modifier,
) {
    var selectedKey: NavKey by remember { mutableStateOf(CurrentWeatherNavKey) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AppNavigationBar {
                BOTTOM_NAV_ITEMS.forEach { (key, item) ->
                    AppNavigationBarItem(
                        selected = selectedKey == key,
                        onClick = { selectedKey = key },
                        icon = {
                            item.unselectedIcon?.let {
                                Icon(imageVector = it, contentDescription = null)
                            }
                        },
                        selectedIcon = {
                            item.selectedIcon?.let {
                                Icon(imageVector = it, contentDescription = null)
                            }
                        },
                        label = item.labelTextId?.let {
                            { Text(stringResource(it)) }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        when (selectedKey) {
            CurrentWeatherNavKey -> CurrentWeatherScreen(Modifier.padding(paddingValues))
        }
    }
}