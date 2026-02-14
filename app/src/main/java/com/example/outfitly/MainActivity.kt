package com.example.outfitly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.outfitly.data.repository.UserPreferencesRepository
import com.example.outfitly.ui.navigation.BottomNavBar
import com.example.outfitly.ui.navigation.NavGraph
import com.example.outfitly.ui.theme.OutfitlyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val prefs by userPreferencesRepository.userPreferences
                .collectAsState(initial = null)
            
            val systemDark = isSystemInDarkTheme()
            val isDark = when (prefs?.darkMode) {
                "dark" -> true
                "light" -> false
                else -> systemDark
            }
            
            OutfitlyTheme(darkTheme = isDark) {
                val navController = rememberNavController()
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController = navController) }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}
