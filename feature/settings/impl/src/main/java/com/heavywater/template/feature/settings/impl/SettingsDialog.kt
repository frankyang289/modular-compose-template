package com.heavywater.template.feature.settings.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heavywater.template.core.model.DarkThemeConfig
import com.heavywater.template.core.model.UserData
import com.heavywater.template.core.designsystem.theme.supportsDynamicTheming

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Settings") },
        text = {
            when (val state = settingsUiState) {
                is SettingsUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                is SettingsUiState.Success -> {
                    SettingsContent(
                        userData = state.userData,
                        onDarkThemeConfigChange = viewModel::updateDarkThemeConfig,
                        onDynamicColorChange = viewModel::updateDynamicColor,
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("OK") }
        },
    )
}

@Composable
private fun SettingsContent(
    userData: UserData,
    onDarkThemeConfigChange: (DarkThemeConfig) -> Unit,
    onDynamicColorChange: (Boolean) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Theme", style = MaterialTheme.typography.titleSmall)
        listOf(
            DarkThemeConfig.FOLLOW_SYSTEM to "System Default",
            DarkThemeConfig.LIGHT to "Light",
            DarkThemeConfig.DARK to "Dark",
        ).forEach { (config, label) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = userData.darkThemeConfig == config,
                        role = Role.RadioButton,
                        onClick = { onDarkThemeConfigChange(config) },
                    )
                    .padding(vertical = 4.dp),
            ) {
                RadioButton(
                    selected = userData.darkThemeConfig == config,
                    onClick = null, // handled by selectable above
                )
                Spacer(Modifier.width(8.dp))
                Text(label)
            }
        }

        if (supportsDynamicTheming()) {
            HorizontalDivider()
            Text("Color", style = MaterialTheme.typography.titleSmall)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Dynamic Color",
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = userData.useDynamicColor,
                    onCheckedChange = onDynamicColorChange,
                )
            }
        }
    }
}
