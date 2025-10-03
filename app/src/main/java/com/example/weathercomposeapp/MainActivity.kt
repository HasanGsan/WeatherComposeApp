package com.example.weathercomposeapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.weathercomposeapp.ui.components.main.MainScreen
import com.example.weathercomposeapp.ui.navigation.mainComponents.RootComponents
import com.example.weathercomposeapp.ui.theme.WeatherComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupSystemBars()
        setContent {
            WeatherComposeAppTheme {
                val rootComponents = remember { RootComponents() }
                MainScreen(rootComponents = rootComponents)
            }
        }
    }

    //Меняет цвет системных компонентов на цветовую схему приложения
    private fun setupSystemBars(){
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK

        windowInsetsController.isAppearanceLightStatusBars = false
        windowInsetsController.isAppearanceLightNavigationBars = false
    }

}