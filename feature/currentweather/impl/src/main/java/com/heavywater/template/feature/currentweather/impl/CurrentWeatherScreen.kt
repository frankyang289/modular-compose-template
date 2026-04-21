package com.heavywater.template.feature.currentweather.impl

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.heavywater.template.core.designsystem.icon.AppIcons
import com.heavywater.template.core.model.WeatherInfo
import com.heavywater.template.core.ui.FullScreenLoader
import com.heavywater.template.feature.settings.impl.SettingsDialog

@Composable
fun CurrentWeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }
    var showRationale by remember { mutableStateOf(false) }
    val currentCity by viewModel.currentCity.collectAsStateWithLifecycle()
    val weather by viewModel.weather.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) viewModel.fetchCurrentCity()
    }

    FullScreenLoader(isLoading = isLoading)

    LaunchedEffect(Unit) {
        val alreadyGranted = ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (alreadyGranted) viewModel.fetchCurrentCity() else showRationale = true
    }

    if (showSettingsDialog) {
        SettingsDialog(onDismiss = { showSettingsDialog = false })
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text("Location Access") },
            text = { Text("This app uses your location to show local weather.") },
            confirmButton = {
                TextButton(onClick = {
                    showRationale = false
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                        )
                    )
                }) { Text("Continue") }
            },
            dismissButton = {
                TextButton(onClick = { showRationale = false }) { Text("Skip") }
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CurrentWeatherTopBar(
                onSettingsClick = { showSettingsDialog = true },
                currentCity = currentCity,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when {
                error != null -> Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                )

                weather != null -> WeatherContent(weather = weather!!)
                !isLoading -> Text("Waiting for location…")
            }
        }
    }
}

@Composable
private fun WeatherContent(weather: WeatherInfo) {
    AsyncImage(
        model = weather.conditionIconUrl,
        contentDescription = weather.conditionText,
        modifier = Modifier.size(80.dp),
    )
    Text(
        text = "${weather.tempCelsius}°C",
        style = MaterialTheme.typography.displayMedium,
    )
    Text(
        text = weather.conditionText,
        style = MaterialTheme.typography.titleMedium,
    )
    Text(text = "Feels like ${weather.feelsLikeCelsius}°C")
    Text(text = "Humidity: ${weather.humidity}%")
    Text(text = "Wind: ${weather.windKph} km/h")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrentWeatherTopBar(
    onSettingsClick: () -> Unit,
    currentCity: String?,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(if (currentCity != null) "Weather in $currentCity" else "Weather") },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = AppIcons.settings,
                    contentDescription = "Settings",
                )
            }
        },
        modifier = modifier,
    )
}