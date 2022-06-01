package com.example.tvmaze.di

import android.content.Context
import androidx.room.Room
import com.example.tvmaze.data.database.AppDatabase
import com.example.tvmaze.data.database.dao.HistorySearchDao
import com.example.tvmaze.data.database.dao.ShowFavoriteDao
import com.example.tvmaze.data.local.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideShowFavoriteDao(appDatabase: AppDatabase): ShowFavoriteDao {
        return appDatabase.showFavoriteDao()
    }

    @Provides
    fun provideHistorySearchDao(appDatabase: AppDatabase): HistorySearchDao {
        return appDatabase.historySearchDaoDao()
    }

    @Provides
    fun provideAppPreferences(@ApplicationContext context: Context,): AppPreferences {
        return AppPreferences(context)
    }

}