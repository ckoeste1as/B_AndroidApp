package com.example.androidtemplate.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.screens.HomeScreen
import com.example.androidtemplate.ui.screens.SettingsScreen
import com.example.androidtemplate.ui.theme.TemplateTheme

private enum class Destination(val route: String, val label: Int, val symbol: String) {
    HOME("home", R.string.home, "⌂"),
    SETTINGS("settings", R.string.settings, "⚙"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateApp(appViewModel: AppViewModel = viewModel()) {
    val state by appViewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val entry by navController.currentBackStackEntryAsState()
    val current = Destination.entries.firstOrNull { it.route == entry?.destination?.route } ?: Destination.HOME

    TemplateTheme(state.themeMode) {
        Scaffold(
            topBar = { TopAppBar(title = { Text(stringResource(current.label)) }) },
            bottomBar = {
                NavigationBar {
                    Destination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = current == destination,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Text(destination.symbol) },
                            label = { Text(stringResource(destination.label)) },
                        )
                    }
                }
            },
        ) { padding ->
            NavHost(navController, startDestination = Destination.HOME.route, modifier = Modifier.padding(padding)) {
                composable(Destination.HOME.route) {
                    HomeScreen(state.count, appViewModel::increment, appViewModel::reset)
                }
                composable(Destination.SETTINGS.route) {
                    SettingsScreen(state.themeMode, appViewModel::setTheme)
                }
            }
        }
    }
}
