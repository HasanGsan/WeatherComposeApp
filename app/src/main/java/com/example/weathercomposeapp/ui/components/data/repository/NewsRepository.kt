package com.example.weathercomposeapp.ui.components.data.repository

import com.example.weathercomposeapp.ui.components.data.models.NewsData

interface NewsRepository {
    suspend fun getNews() : List<NewsData>
    suspend fun getNewsByCategory(category: String): List<NewsData>
    suspend fun addFavorite(newsId: String)
    suspend fun getFavorites(): List<NewsData>
}