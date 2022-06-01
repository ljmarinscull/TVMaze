package com.example.tvmaze.data.database

import com.example.tvmaze.data.model.Show
import com.example.tvmaze.data.model.ShowFavorite
import com.example.tvmaze.utils.Resource

interface IShowFavoriteDataSource {
    suspend fun getFavorites(): Resource<List<ShowFavorite>>
    suspend fun deleteShowFromFavorite(show: ShowFavorite)
    suspend fun deleteShowFromFavoriteIfExist(show: Show)
    suspend fun addShowToFavoriteIfNotExist(show: Show)
    suspend fun checkIfItIsFavorite(show: Show): Boolean
}