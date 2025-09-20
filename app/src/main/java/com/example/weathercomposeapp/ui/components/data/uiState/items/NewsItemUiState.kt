package com.example.weathercomposeapp.ui.components.data.uiState.items

import com.example.weathercomposeapp.ui.components.data.models.NewsData

data class NewsItemUiState(
    val newsData: NewsData,
    val isRead: Boolean = false,
    val isFavorite: Boolean = false
)