package com.example.outfitly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.outfitly.ui.screens.home.HomeScreen
import com.example.outfitly.ui.screens.wardrobe.WardrobeScreen
import com.example.outfitly.ui.screens.settings.SettingsScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Wardrobe : Screen("wardrobe")
    data object Settings : Screen("settings")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Wardrobe.route) {
            WardrobeScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
