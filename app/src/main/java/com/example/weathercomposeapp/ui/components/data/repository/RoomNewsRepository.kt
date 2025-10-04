package com.example.weathercomposeapp.ui.components.data.repository

import com.example.weathercomposeapp.ui.components.data.local.dao.NewsStatusDao
import com.example.weathercomposeapp.ui.components.data.local.entity.FavoriteEntity
import com.example.weathercomposeapp.ui.components.data.local.entity.ReadPostEntity
import com.example.weathercomposeapp.ui.components.data.models.NewsData
import kotlinx.coroutines.flow.first

class RoomNewsRepository(
    private val newsStatusDao: NewsStatusDao,
    private val fakeNewsRepository: FakeNewsRepository = FakeNewsRepository
): NewsRepository {

    override suspend fun getNews(): List<NewsData> {
        return fakeNewsRepository.getNews()
    }

    override suspend fun getNewsByCategory(category: String): List<NewsData> {
        return fakeNewsRepository.getNewsByCategory(category)
    }

    override suspend fun addFavorite(newsId: String) {
       newsStatusDao.addFavorite(FavoriteEntity(newsId))
    }

    override suspend fun removeFavorite(newsId: String) {
       newsStatusDao.removeFavorite(newsId)
    }

    override suspend fun toggleFavorite(newsId: String) {
        val isFav = newsStatusDao.isFavorite(newsId) > 0
        if (isFav) {
            newsStatusDao.removeFavorite(newsId)
        } else {
            newsStatusDao.addFavorite(FavoriteEntity(newsId))
        }
    }

    override suspend fun getFavorites(): List<NewsData> {
        val allNews = fakeNewsRepository.getNews()

        val favoriteIds = newsStatusDao.getAllFavorites()
            .first()
            .map { it.newsId }

        return allNews.filter { it.id in favoriteIds }
    }

    override suspend fun isFavorite(newsId: String): Boolean {
        return newsStatusDao.isFavorite(newsId) > 0
    }

    override suspend fun asRead(newsId: String) {
        val isNowRead = newsStatusDao.isRead(newsId) > 0
        if(isNowRead){
            newsStatusDao.removeReadPost(newsId)
        } else {
            newsStatusDao.addReadPost(ReadPostEntity(newsId))
        }
    }

    override suspend fun isRead(newsId: String): Boolean {
        return newsStatusDao.isRead(newsId) > 0
    }

}