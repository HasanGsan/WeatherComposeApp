package com.example.weathercomposeapp.ui.components.data.navigationData

import androidx.compose.ui.graphics.painter.Painter
import com.example.weathercomposeapp.ui.navigation.screens.Screen

data class BottomNavItem (
    val title: String,
    val image: Painter,
    val route: Screen
)