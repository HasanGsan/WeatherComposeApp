package com.example.weathercomposeapp.ui.components.data.navigationData

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem (
    val title: String,
    val image: Painter,
    val route: String
)