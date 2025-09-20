package com.example.weathercomposeapp.ui.components.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weathercomposeapp.ui.components.data.navigationData.BottomNavItem
import com.example.weathercomposeapp.ui.theme.WeatherComposeAppTheme
import com.example.weathercomposeapp.R
import com.example.weathercomposeapp.ui.components.screens.FavoriteScreen
import com.example.weathercomposeapp.ui.components.screens.NewsScreen
import com.example.weathercomposeapp.ui.components.screens.WeatherScreen
import com.example.weathercomposeapp.ui.navigation.Screen
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weathercomposeapp.ui.theme.BlackBackground
import com.example.weathercomposeapp.ui.theme.WhiteColor


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupSystemBars()
        setContent {
            WeatherComposeAppTheme {
                MainScreen()
            }
        }
    }

    //Меняет цвет системных компонентов на цветовую схему приложения
    private fun setupSystemBars(){
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK

        windowInsetsController.isAppearanceLightStatusBars = false
        windowInsetsController.isAppearanceLightNavigationBars = false
    }

}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.NEWS.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.WEATHER.name) { WeatherScreen() }
            composable(Screen.NEWS.name) { NewsScreen() }
            composable(Screen.FAVORITE.name) { FavoriteScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavController) {
    val items = listOf(
        BottomNavItem(
            title = "Погода",
            image = painterResource(R.drawable.ic_weather),
            route = Screen.WEATHER.name
        ),
        BottomNavItem(
            title = "Новости",
            image = painterResource(R.drawable.ic_new),
            route = Screen.NEWS.name
        ),
        BottomNavItem(
            title = "Избранное",
            image = painterResource(R.drawable.ic_favorite_black),
            route = Screen.FAVORITE.name
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = BlackBackground
    ) {
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if(isSelected) WhiteColor.copy(alpha = 0.2f) else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) Color.White.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = item.image,
                                contentDescription = item.title,
                                modifier = Modifier.size(20.dp),
                                tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f)
                            )

                            Text(
                                text = item.title,
                                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
                selected = currentDestination?.hierarchy?.any {it.route == item.route} == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                   }
               },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}




