package com.example.weathercomposeapp.ui.components.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercomposeapp.ui.components.data.repository.FakeNewsRepository
import com.example.weathercomposeapp.ui.components.data.repository.NewsRepository
import com.example.weathercomposeapp.ui.components.data.repository.RoomNewsRepository
import com.example.weathercomposeapp.ui.components.data.uiState.NewsUiState
import com.example.weathercomposeapp.ui.components.data.uiState.items.NewsItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init { loadNews() }

    fun loadNews(category: String? = null) {
        viewModelScope.launch {
            _uiState.update {it.copy(isLoading = true, errorMessage = null)}

            try {
                val list = if(category.isNullOrEmpty()) repository.getNews() else repository.getNewsByCategory(category)
                val items = list.map { newsData ->
                    NewsItemUiState(
                        newsData = newsData,
                        isRead = repository.isRead(newsData.id),
                        isFavorite = repository.isFavorite(newsData.id)
                    )
                }
                _uiState.value = NewsUiState(isLoading = false, newsItems = items)
            } catch (t: Throwable) {
                _uiState.value = NewsUiState(
                    isLoading = false,
                    newsItems = emptyList(),
                    errorMessage = t.message
                )
            }
        }
    }

    fun toggleRead(newsId: String) {
        viewModelScope.launch {
            repository.asRead(newsId)
            val nowRead = repository.isRead(newsId)
            _uiState.update { current ->
                val updated = current.newsItems.map { item ->
                    if (item.newsData.id == newsId) item.copy(isRead = nowRead) else item
                }
                current.copy(newsItems = updated)
            }
        }
    }

    fun toggleFavorite(newsId: String) {
        viewModelScope.launch {
            repository.addFavorite(newsId)
            _uiState.update { current ->
                val updated = current.newsItems.map { item ->
                    if (item.newsData.id == newsId) item.copy(isFavorite = !item.isFavorite) else item
                }
                current.copy(newsItems = updated)
            }
        }
    }

    //Синхр между экранами
    fun refreshFavorites() {
        viewModelScope.launch {
            _uiState.update { current ->
                val updated = current.newsItems.map { item ->
                    item.copy(
                        isFavorite = repository.getFavorites().any { favorite -> favorite.id == item.newsData.id }
                    )
                }
                current.copy(newsItems = updated)
            }
        }
    }
}