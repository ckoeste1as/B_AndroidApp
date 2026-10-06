package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.theme.TemplateTheme

@Composable
fun HomeScreen(count: Int, onIncrement: () -> Unit, onReset: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(stringResource(R.string.welcome), style = MaterialTheme.typography.headlineMedium)
        Text(stringResource(R.string.intro), style = MaterialTheme.typography.bodyLarge)
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(stringResource(R.string.counter, count), style = MaterialTheme.typography.titleLarge)
                Button(onClick = onIncrement) { Text(stringResource(R.string.increment)) }
                OutlinedButton(onClick = onReset, enabled = count > 0) { Text(stringResource(R.string.reset)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    TemplateTheme { HomeScreen(3, {}, {}) }
}
