package com.example.tvmaze.di

import com.example.tvmaze.data.api.IShowDataSource
import com.example.tvmaze.data.api.ShowDataSource
import com.example.tvmaze.data.database.HistorySearchDataSource
import com.example.tvmaze.data.database.IHistorySearchDataSource
import com.example.tvmaze.data.database.IShowFavoriteDataSource
import com.example.tvmaze.data.database.ShowFavoriteDataSource
import com.example.tvmaze.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideIShowRepository(
        showRepository: ShowRepository
    ): IShowRepository

    @Singleton
    @Binds
    abstract fun provideIShowFavoriteRepository(
        showFavoriteRepository: ShowFavoriteRepository
    ): IShowFavoriteRepository

    @Singleton
    @Binds
    abstract fun provideIHistorySearchRepository(
        historySearchRepository: HistorySearchRepository
    ): IHistorySearchRepository

    @Singleton
    @Binds
    abstract fun provideIShowDataSource(
        mainDataSource: ShowDataSource
    ): IShowDataSource

    @Singleton
    @Binds
    abstract fun provideIShowFavoriteDataSource(
        showFavoriteDataSource: ShowFavoriteDataSource
    ): IShowFavoriteDataSource

    @Singleton
    @Binds
    abstract fun provideIHistorySearchDataSource(
        historySearchDataSource: HistorySearchDataSource
    ): IHistorySearchDataSource
}