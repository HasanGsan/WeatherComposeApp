package com.example.weathercomposeapp.ui.navigation.mainComponents

import com.example.weathercomposeapp.ui.navigation.screens.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RootComponents {

    private val _currentScreen = MutableStateFlow(Screen.NEWS)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    fun showNews() {_currentScreen.value = Screen.NEWS}

    fun showWeather() {_currentScreen.value = Screen.WEATHER}

    fun showFavorite() {_currentScreen.value = Screen.FAVORITE}

}