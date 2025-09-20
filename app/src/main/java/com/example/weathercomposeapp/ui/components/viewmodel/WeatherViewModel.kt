package com.example.weathercomposeapp.ui.components.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weathercomposeapp.ui.components.data.models.WeatherData
import com.example.weathercomposeapp.ui.components.data.uiState.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _weatherData = MutableStateFlow(WeatherData())
    val weatherData: StateFlow<WeatherData> = _weatherData.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<WeatherData>>(emptyList())
    val searchHistory: StateFlow<List<WeatherData>> = _searchHistory.asStateFlow()

    fun updateCity(newCity: String) {
        _weatherData.update { it.copy(city = newCity) }
        updateFormValidity()
    }

    fun updateDegrees(newDegrees: String) {
        _weatherData.update { it.copy(degrees = newDegrees) }
        updateFormValidity()
    }

    fun updateDescriptionWeather() {
        val city = weatherData.value.city
        val degrees = weatherData.value.degrees.toIntOrNull() ?: 0
        val description = when (degrees) {
            in -50..14 -> "Сейчас в г. $city Холодно"
            in 15..24 -> "Сейчас в г. $city Нормально"
            in 25..50 -> "Сейчас в г. $city Жарко"
            else -> "Сейчас в г. $city Катастрофа"
        }
        _weatherData.update { it.copy(weatherDescription = description) }
        _weatherData.update { it.copy(degrees = "${weatherData.value.degrees}°C") }
    }

    fun addToHistory() {
        val currentHistory = searchHistory.value.toMutableList()
        currentHistory.removeAll { it.city == weatherData.value.city }
        currentHistory.add(0, weatherData.value.copy())
        if (currentHistory.size > 5) currentHistory.removeAt(currentHistory.lastIndex)
        _searchHistory.value = currentHistory
    }

    fun onBlankWeatherClick() {
        updateDescriptionWeather()
        hideInputs()
    }

    fun hideInputs() {
        _uiState.update { it.copy(inputsVisible = false, buttonsVisible = false) }
    }

    fun showInputs() {
        _uiState.update { it.copy(inputsVisible = true, buttonsVisible = true) }
    }

    fun clearAndCreateNewRequest() {
        showInputs()
        addToHistory()
        _weatherData.value = WeatherData()
        _uiState.update { it.copy(isFormValid = false) }
    }

    private fun updateFormValidity() {
        val cityValid = weatherData.value.city.isNotEmpty()
        val degreesValid = weatherData.value.degrees.isNotEmpty()
        _uiState.update { it.copy(isFormValid = cityValid && degreesValid) }
    }
}