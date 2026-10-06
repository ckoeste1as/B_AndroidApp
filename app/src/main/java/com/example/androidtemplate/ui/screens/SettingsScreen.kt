package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.ThemeMode
import com.example.androidtemplate.ui.theme.TemplateTheme

@Composable
fun SettingsScreen(mode: ThemeMode, onThemeChange: (ThemeMode) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(stringResource(R.string.appearance), style = MaterialTheme.typography.headlineMedium)
        Column(Modifier.selectableGroup()) {
            ThemeMode.entries.forEach { option ->
                val label = when (option) {
                    ThemeMode.SYSTEM -> R.string.theme_system
                    ThemeMode.LIGHT -> R.string.theme_light
                    ThemeMode.DARK -> R.string.theme_dark
                }
                Row(
                    Modifier.fillMaxWidth().heightIn(min = 56.dp)
                        .selectable(selected = mode == option, role = Role.RadioButton, onClick = { onThemeChange(option) })
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = mode == option, onClick = null)
                    Spacer(Modifier.width(12.dp))
                    Text(stringResource(label))
                }
            }
        }
        Text(stringResource(R.string.settings_hint), style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsPreview() {
    TemplateTheme { SettingsScreen(ThemeMode.SYSTEM, {}) }
}
