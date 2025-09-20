package com.example.weathercomposeapp.ui.components.data.uiState

import com.example.weathercomposeapp.ui.components.data.uiState.items.NewsItemUiState

data class NewsUiState (
    val isLoading: Boolean = false,
    val newsItems: List<NewsItemUiState> = emptyList(),
    val errorMessage: String? = null,
)