package com.example.weathercomposeapp.ui.components.data

data class WeatherData(
    val city: String = "",
    val degrees: String = "",
    val timestamp: Long = System.currentTimeMillis()
)