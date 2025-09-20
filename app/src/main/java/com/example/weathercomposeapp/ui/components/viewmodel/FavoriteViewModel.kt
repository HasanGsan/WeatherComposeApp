package com.example.weathercomposeapp.ui.components.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercomposeapp.ui.components.data.repository.FakeNewsRepository
import com.example.weathercomposeapp.ui.components.data.repository.NewsRepository
import com.example.weathercomposeapp.ui.components.data.uiState.NewsUiState
import com.example.weathercomposeapp.ui.components.data.uiState.items.NewsItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: NewsRepository = FakeNewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _selectedTag = MutableStateFlow("Все")
    val selectedTag: StateFlow<String> = _selectedTag.asStateFlow()

    private val _allTags = MutableStateFlow<List<String>>(emptyList())
    val allTags: StateFlow<List<String>> = _allTags.asStateFlow()

    init {
        loadFavorites()
        loadTags()
    }

    private fun loadTags() {
        viewModelScope.launch {
            val allNews = repository.getNews()
            val tags = allNews.map { it.tags }.distinct()
            _allTags.value = listOf("Все") + tags
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val favoriteNews = repository.getFavorites()
            val items = favoriteNews.map { newsData ->
                NewsItemUiState(
                    newsData = newsData,
                    isFavorite = true
                )
            }
            _uiState.value = NewsUiState(newsItems = items)
        }
    }

    fun selectTag(tag: String) {
        _selectedTag.value = tag
        viewModelScope.launch {
            val favoriteNews = repository.getFavorites()
            val items = favoriteNews.map { NewsItemUiState(it, isFavorite = true) }
            val filtered = if (tag == "Все") items else items.filter { it.newsData.tags.contains(tag) }
            _uiState.value = NewsUiState(newsItems = filtered)
        }
    }

    fun toggleFavorite(newsId: String) {
        viewModelScope.launch {
            repository.addFavorite(newsId)
            loadFavorites()
        }
    }
}