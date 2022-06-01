package com.example.tvmaze.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tvmaze.data.database.dao.HistorySearchDao
import com.example.tvmaze.data.database.dao.ShowFavoriteDao

@Database(
    entities = [ShowFavoriteEntity::class,HistorySearchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun showFavoriteDao(): ShowFavoriteDao
    abstract fun historySearchDaoDao(): HistorySearchDao

    companion object {
        const val DATABASE_NAME = "database"

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }
}