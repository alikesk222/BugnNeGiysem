package com.example.outfitly.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.outfitly.ui.theme.*

data class BottomNavItem(
    val route: String,
    val title: String,
    val emoji: String
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home.route, "Ana Sayfa", "🏠"),
    BottomNavItem(Screen.Wardrobe.route, "Gardırop", "👔"),
    BottomNavItem(Screen.Settings.route, "Ayarlar", "⚙️")
)

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar(
        containerColor = CardBackground,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            val animatedColor by animateColorAsState(
                targetValue = if (selected) Primary else OnSurface.copy(alpha = 0.6f),
                label = "navColor"
            )
            
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Text(
                        text = item.emoji,
                        fontSize = 24.sp
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = animatedColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    indicatorColor = Primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}
