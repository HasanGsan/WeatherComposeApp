package com.example.weathercomposeapp.ui.components.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.weathercomposeapp.ui.components.screens.FavoriteScreen
import com.example.weathercomposeapp.ui.components.screens.NewsScreen
import com.example.weathercomposeapp.ui.components.screens.WeatherScreen
import com.example.weathercomposeapp.ui.navigation.bottomBar.BottomNavigationBar
import com.example.weathercomposeapp.ui.navigation.mainComponents.RootComponents
import com.example.weathercomposeapp.ui.navigation.screens.Screen

@Composable
fun MainScreen(rootComponents: RootComponents) {

    val currentScreen by rootComponents.currentScreen.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(rootComponents = rootComponents)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen){
                Screen.WEATHER -> WeatherScreen()
                Screen.NEWS -> NewsScreen()
                Screen.FAVORITE -> FavoriteScreen()
            }
        }
    }

}

