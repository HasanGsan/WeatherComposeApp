package com.example.weathercomposeapp.ui.components.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weathercomposeapp.ui.components.data.WeatherData
import com.example.weathercomposeapp.ui.components.data.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<WeatherData>>(emptyList())
    val searchHistory: StateFlow<List<WeatherData>> = _searchHistory.asStateFlow()


    fun updateCity(newCity: String){
        _uiState.value = uiState.value.copy(city = newCity)
        updateFormValidity()
    }

    fun updateDegrees(newDegrees: String){
        _uiState.value = uiState.value.copy(degrees = newDegrees)
        updateFormValidity()
    }

    fun updateDescriptionWeather(){
        val city = uiState.value.city
        val degrees = uiState.value.degrees.toIntOrNull() ?: 0
        val description = when (degrees) {
            in -50..14 -> "Сейчас в г. $city Холодно"
            in 15..24 -> "Сейчас в г. $city Нормально"
            in 25..50 -> "Сейчас в г. $city Жарко"
            else -> "Сейчас в г. $city Катастрофа"
        }
        _uiState.value = uiState.value.copy(weatherDescription = description)
        _uiState.value = uiState.value.copy(degrees = "${uiState.value.degrees}°C")
    }

    fun addToHistory(weatherData: WeatherData) {
        val currentHistory = searchHistory.value.toMutableList()
            currentHistory.removeAll { it.city == weatherData.city }
            currentHistory.add(0, weatherData)
            if(currentHistory.size > 5) currentHistory.removeAt(currentHistory.lastIndex)
        _searchHistory.value = currentHistory
    }

    fun onBlankWeatherClick(){
        updateDescriptionWeather()
        hideInputs()
    }

    fun hideInputs() {
        _uiState.value = uiState.value.copy(inputsVisible = false, buttonsVisible = false)
    }

    fun showInputs() {
        _uiState.value = uiState.value.copy(inputsVisible = true, buttonsVisible = true)
    }

     fun clearAndCreateNewRequest(){
        showInputs()
         addToHistory(
             WeatherData(
                 city = uiState.value.city,
                 degrees = uiState.value.degrees,
             )
         )
         _uiState.value = uiState.value.copy(city = "", degrees = "", weatherDescription = "", isFormValid = false)
    }

    private fun updateFormValidity() {
        val cityValid = uiState.value.city.isNotEmpty()
        val degreesValid = uiState.value.degrees.isNotEmpty()
        _uiState.value = uiState.value.copy(
            isFormValid = cityValid && degreesValid
        )
    }

}