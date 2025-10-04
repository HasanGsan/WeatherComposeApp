package com.example.weathercomposeapp.ui.components.data.repository

import androidx.compose.runtime.mutableStateOf
import com.example.weathercomposeapp.ui.components.data.models.NewsData
import com.example.weathercomposeapp.R
import kotlinx.coroutines.delay

object FakeNewsRepository : NewsRepository {

    private val readIds = mutableSetOf<String>()

    private val fakeNews = listOf(
        NewsData(
            id = "1",
            title = "Тайные улочки Барселоны",
            preview = R.drawable.ic_barcelona_icon,
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. \nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            timeCreated = 5,
            postCategory = "Пост",
            tags = "Культура",
        ),
        NewsData(
            id = "2",
            title = "Как проходит рабочий день PM из",
            preview = R.drawable.ic_pm_icon,
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. \nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            timeCreated = 10,
            postCategory = "Эссе",
            tags = "Технологии",
        ),
        NewsData(
            id = "3",
            title = "Как я разрабатывал это приложение",
            preview = R.drawable.ic_travel_icon,
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. \nLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            timeCreated = 30,
            postCategory = "Статья",
            tags = "Путешествие",
        )
    )

    private val favoriteIds = mutableSetOf<String>()

    override suspend fun getNews(): List<NewsData> {
        delay(1000)
        return fakeNews
    }

    override suspend fun getNewsByCategory(category: String): List<NewsData> {
        delay(500)
        return if (category == "Все"){ fakeNews } else { fakeNews.filter {it.postCategory == category} }
    }

    override suspend fun addFavorite(newsId: String) {
        favoriteIds.add(newsId)
    }

    override suspend fun removeFavorite(newsId: String) {
        favoriteIds.remove(newsId)
    }

    override suspend fun getFavorites(): List<NewsData> {
        delay(100)
        return fakeNews.filter { favoriteIds.contains(it.id) }
    }

    override suspend fun toggleFavorite(newsId: String) {
        if (favoriteIds.contains(newsId)) {
            favoriteIds.remove(newsId)
        } else {
            favoriteIds.add(newsId)
        }
    }


    override suspend fun isFavorite(newsId: String): Boolean {
        return favoriteIds.contains(newsId)
    }

    override suspend fun asRead(newsId: String) {
        if (readIds.contains(newsId)) {
            readIds.remove(newsId)
        } else {
            readIds.add(newsId)
        }
    }

    override suspend fun isRead(newsId: String): Boolean {
        return readIds.contains(newsId)
    }
}