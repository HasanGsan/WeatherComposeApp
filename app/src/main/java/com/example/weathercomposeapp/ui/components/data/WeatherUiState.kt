package com.example.weathercomposeapp.ui.components.data

data class WeatherUiState (
    val city: String = "",
    val degrees: String = "",
    val weatherDescription: String = "",
    val isFormValid: Boolean = false,
    val inputsVisible: Boolean = true,
    val buttonsVisible: Boolean = true
)