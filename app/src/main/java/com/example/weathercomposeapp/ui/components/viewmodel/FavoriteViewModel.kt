package com.example.weathercomposeapp.ui.components.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercomposeapp.ui.components.data.repository.FakeNewsRepository
import com.example.weathercomposeapp.ui.components.data.repository.NewsRepository
import com.example.weathercomposeapp.ui.components.data.uiState.NewsUiState
import com.example.weathercomposeapp.ui.components.data.uiState.items.NewsItemUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class FavoriteViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _selectedTag = MutableStateFlow("Все")
    val selectedTag: StateFlow<String> = _selectedTag.asStateFlow()

    private val _allTags = MutableStateFlow<List<String>>(emptyList())
    val allTags: StateFlow<List<String>> = _allTags.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
            println("DEBUG: Начало загрузки в ${System.currentTimeMillis()}")

            try {
                val favoritesDeferred = async { repository.getFavorites() }
                val tagsDeferred = async {
                    val allNews =  repository.getNews()
                    allNews.map {it.tags}.distinct()
                }

                val favorite = favoritesDeferred.await()
                val tags = tagsDeferred.await()

                println("DEBUG: Данные получены за ${System.currentTimeMillis() - startTime}ms")

                withContext(Dispatchers.Main) {
                    println("DEBUG: Начало обновления UI в ${System.currentTimeMillis()}")
                    _allTags.value = listOf("Все") + tags
                    _uiState.value = NewsUiState(
                        newsItems = favorite.map { news ->
                            NewsItemUiState(newsData = news, isFavorite = true)
                        },
                        isLoading = false
                    )
                    println("DEBUG: UI обновлен в ${System.currentTimeMillis()}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.value = NewsUiState(isLoading = false, errorMessage = "Ошибка загрузки новостей")
                }
            }
        }
    }

    fun selectTag(tag: String) {
        _selectedTag.value = tag
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrentList()
        }
    }

    fun toggleFavorite(newsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleFavorite(newsId)
            updateCurrentList()
        }
    }

    private suspend fun updateCurrentList() {
        val currentTag = _selectedTag.value
        val favoriteNews = repository.getFavorites()
        val items = favoriteNews.map { news ->
            NewsItemUiState(newsData = news, isFavorite = true)
        }
        val filtered = if(currentTag == "Все") items else  items.filter {
            it.newsData.tags.contains(currentTag)
        }
        _uiState.value = NewsUiState(newsItems = filtered)
    }
}