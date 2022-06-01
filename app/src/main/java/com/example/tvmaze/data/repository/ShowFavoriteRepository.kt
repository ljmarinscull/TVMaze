package com.example.tvmaze.data.repository

import com.example.tvmaze.data.database.IShowFavoriteDataSource
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.data.model.ShowFavorite
import com.example.tvmaze.utils.Resource
import javax.inject.Inject

class ShowFavoriteRepository @Inject constructor(
    private val dataSource: IShowFavoriteDataSource
) : IShowFavoriteRepository {
    override suspend fun getFavorites(): Resource<List<ShowFavorite>> = dataSource.getFavorites()
    override suspend fun deleteShowFromFavorite(show: ShowFavorite) = dataSource.deleteShowFromFavorite(show)
    override suspend fun deleteShowFromFavoriteIfExist(show: Show) = dataSource.deleteShowFromFavoriteIfExist(show)
    override suspend fun addShowToFavoriteIfNotExist(show: Show) = dataSource.addShowToFavoriteIfNotExist(show)
    override suspend fun checkIfItIsFavorite(show: Show): Boolean = dataSource.checkIfItIsFavorite(show)
}