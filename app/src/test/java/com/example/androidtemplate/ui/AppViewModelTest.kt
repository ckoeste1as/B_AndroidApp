package com.example.androidtemplate.ui

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Test

class AppViewModelTest {
    @Test
    fun counterCanBeIncrementedAndReset() {
        val model = AppViewModel(SavedStateHandle())
        model.increment()
        model.increment()
        assertEquals(2, model.uiState.value.count)
        model.reset()
        assertEquals(0, model.uiState.value.count)
    }

    @Test
    fun stateIsRestoredFromSavedState() {
        val handle = SavedStateHandle()
        val original = AppViewModel(handle)
        original.increment()
        original.setTheme(ThemeMode.DARK)
        val restored = AppViewModel(SavedStateHandle(mapOf("count" to handle.get<Int>("count"), "theme" to handle.get<String>("theme"))))
        assertEquals(AppUiState(1, ThemeMode.DARK), restored.uiState.value)
    }

    @Test
    fun unknownThemeFallsBackToSystem() {
        val model = AppViewModel(SavedStateHandle(mapOf("theme" to "unknown")))
        assertEquals(ThemeMode.SYSTEM, model.uiState.value.themeMode)
    }
}
