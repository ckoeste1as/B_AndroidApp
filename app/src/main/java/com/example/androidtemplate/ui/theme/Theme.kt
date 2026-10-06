package com.example.androidtemplate.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.androidtemplate.ui.ThemeMode

private val LightColors = lightColorScheme(primary = Color(0xFF315DA8), secondary = Color(0xFF565F71))
private val DarkColors = darkColorScheme(primary = Color(0xFFA9C7FF), secondary = Color(0xFFBEC7DC))

@Composable
fun TemplateTheme(mode: ThemeMode = ThemeMode.SYSTEM, content: @Composable () -> Unit) {
    val dark = when (mode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
    val colors = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (dark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else if (dark) DarkColors else LightColors

    MaterialTheme(colorScheme = colors, typography = Typography(), content = content)
}
