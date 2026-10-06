package com.example.androidtemplate

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TemplateAppTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun counterSurvivesNavigationAndCanBeReset() {
        compose.onNodeWithText(compose.activity.getString(R.string.increment)).performClick()
        compose.onNodeWithText(compose.activity.getString(R.string.counter, 1)).assertIsDisplayed()
        compose.onNodeWithText(compose.activity.getString(R.string.settings)).performClick()
        compose.onNodeWithText(compose.activity.getString(R.string.theme_dark)).performClick()
        compose.onNodeWithText(compose.activity.getString(R.string.home)).performClick()
        compose.onNodeWithText(compose.activity.getString(R.string.counter, 1)).assertIsDisplayed()
        compose.onNodeWithText(compose.activity.getString(R.string.reset)).performClick()
        compose.onNodeWithText(compose.activity.getString(R.string.counter, 0)).assertIsDisplayed()
    }
}
