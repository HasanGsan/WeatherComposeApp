package com.example.weathercomposeapp.ui.components.data.local.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercomposeapp.ui.components.data.repository.NewsRepository
import com.example.weathercomposeapp.ui.components.viewmodel.FavoriteViewModel

class FavoriteViewModelFactory(
private val repository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}