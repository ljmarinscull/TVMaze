package com.example.tvmaze.di

import com.example.tvmaze.data.api.model.EpisodeJO
import com.example.tvmaze.data.api.model.ShowJO
import com.example.tvmaze.data.api.model.ShowSearchedJO
import com.example.tvmaze.data.database.HistorySearchEntity
import com.example.tvmaze.data.database.ShowFavoriteEntity
import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.data.model.HistorySearch
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.data.model.ShowFavorite
import com.example.tvmaze.utils.mappers.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    @Singleton
    abstract fun provideIMapperS(
        showMapper: ShowMapper
    ): IMapper<ShowJO, Show>

    @Binds
    @Singleton
    abstract fun provideIListMapperS(
        showListMapper: ShowListMapper
    ): IListMapper<ShowJO, Show>

    @Binds
    @Singleton
    abstract fun provideIMapperE(
        episodeMapper: EpisodeMapper
    ): IMapperOneWay<EpisodeJO, Episode>

    @Binds
    @Singleton
    abstract fun provideIMapperH(
        historySearchMapper: HistorySearchMapper
    ): IMapper<HistorySearch, HistorySearchEntity>

    @Binds
    @Singleton
    abstract fun provideIListMapperE(
        episodeListMapper: EpisodeListMapper
    ): IListMapperOneWay<EpisodeJO, Episode>

    @Binds
    @Singleton
    abstract fun provideIMapperSF(
        showFavoriteMapper: ShowFavoriteMapper
    ): IMapper<ShowFavorite, ShowFavoriteEntity>

    @Binds
    @Singleton
    abstract fun provideIMapperSS(
        showSearchedMapper: ShowSearchedMapper
    ): IMapperOneWay<ShowSearchedJO, Show>

    @Binds
    @Singleton
    abstract fun provideIListMapperSF(
        showFavoriteListMapper: ShowFavoriteListMapper
    ): IListMapper<ShowFavorite, ShowFavoriteEntity>

    @Binds
    @Singleton
    abstract fun provideIListMapperH(
        historySearchListMapper: HistorySearchListMapper
    ): IListMapper<HistorySearch, HistorySearchEntity>

    @Binds
    @Singleton
    abstract fun provideIListMapperSS(
        showSearchedListMapper: ShowSearchedListMapper
    ): IListMapperOneWay<ShowSearchedJO, Show>
}