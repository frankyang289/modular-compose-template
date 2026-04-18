package com.heavywater.core.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8B418F)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFA9FE)
)

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        androidTheme -> if (darkTheme) DarkColorScheme else LightColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}