package com.heavywater.core.designsystem

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.heavywater.template.core.designsystem.component.AppNavigationBar
import com.heavywater.template.core.designsystem.component.AppNavigationBarItem
import com.heavywater.template.core.designsystem.icon.AppIcons
import com.heavywater.template.core.screenshot_test.captureMultiTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [Build.VERSION_CODES.S])
class NavigationScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun navigationBar_multiTheme() {
        composeTestRule.captureMultiTheme("NavigationBar") {
            AppNavigationBar {
                AppNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = AppIcons.currentWeatherBorder,
                            contentDescription = "Current Weather",
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = AppIcons.currentWeather,
                            contentDescription = "Current Weather",
                        )
                    },
                    label = { Text("Current Weather") },
                    selected = true,
                    onClick = {},
                )
                AppNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = AppIcons.settings,
                            contentDescription = "Settings",
                        )
                    },
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {},
                )
            }
        }
    }
}
