package com.example.weathercomposeapp.ui.components.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weathercomposeapp.ui.components.data.local.dao.NewsStatusDao
import com.example.weathercomposeapp.ui.components.data.local.entity.FavoriteEntity
import com.example.weathercomposeapp.ui.components.data.local.entity.ReadPostEntity

@Database(
    entities = [FavoriteEntity::class, ReadPostEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun newsStatusDao(): NewsStatusDao
}