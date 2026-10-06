package com.example.androidtemplate.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ThemeMode { SYSTEM, LIGHT, DARK }

data class AppUiState(val count: Int = 0, val themeMode: ThemeMode = ThemeMode.SYSTEM)

class AppViewModel(private val savedState: SavedStateHandle) : ViewModel() {
    private val mutableState = MutableStateFlow(
        AppUiState(
            count = savedState["count"] ?: 0,
            themeMode = savedState.get<String>("theme")?.let { stored ->
                ThemeMode.entries.firstOrNull { it.name == stored }
            } ?: ThemeMode.SYSTEM,
        )
    )
    val uiState = mutableState.asStateFlow()

    fun increment() = setCount((uiState.value.count.toLong() + 1).coerceAtMost(Int.MAX_VALUE.toLong()).toInt())
    fun reset() = setCount(0)

    private fun setCount(count: Int) {
        savedState["count"] = count
        mutableState.update { it.copy(count = count) }
    }

    fun setTheme(mode: ThemeMode) {
        savedState["theme"] = mode.name
        mutableState.update { it.copy(themeMode = mode) }
    }
}
