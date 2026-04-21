package com.heavywater.template

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.heavywater.template.ui.TemplateApp
import com.heavywater.template.util.isSystemInDarkTheme
import com.heavywater.template.core.designsystem.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme,
                androidTheme = MainActivityUiState.Loading.shouldUseAndroidTheme,
                disableDynamicTheming = MainActivityUiState.Loading.shouldDisableDynamicTheming,
            )
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    themeSettings = ThemeSettings(
                        darkTheme = uiState.shouldUseDarkTheme(
                            resources.configuration.isSystemInDarkTheme
                        ),
                        androidTheme = uiState.shouldUseAndroidTheme,
                        disableDynamicTheming = uiState.shouldDisableDynamicTheming,
                    )
                }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value.shouldKeepSplashScreen()
        }
        setContent {
            AppTheme(
                darkTheme = themeSettings.darkTheme,
                androidTheme = themeSettings.androidTheme,
                disableDynamicTheming = themeSettings.disableDynamicTheming,
            ) {
                TemplateApp()
            }
        }
    }
}

data class ThemeSettings(
    val darkTheme: Boolean,
    val androidTheme: Boolean,
    val disableDynamicTheming: Boolean,
)