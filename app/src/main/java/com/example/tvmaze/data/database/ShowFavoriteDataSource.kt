package com.example.tvmaze.data.database

import com.example.tvmaze.data.database.dao.ShowFavoriteDao
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.data.model.ShowFavorite
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.mappers.IListMapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowFavoriteDataSource @Inject constructor(
    private val showFavoriteDao: ShowFavoriteDao,
    private val showFavoriteListMapper: IListMapper<ShowFavorite, ShowFavoriteEntity>,
) : IShowFavoriteDataSource {

    override suspend fun getFavorites(): Resource<List<ShowFavorite>> = withContext(IO){
        val result = showFavoriteDao.getAll()
        val mappedResult = showFavoriteListMapper.mapReverse(result)
        return@withContext Resource.Success(mappedResult)
    }

    override suspend fun deleteShowFromFavorite(show: ShowFavorite) = withContext(IO){
        showFavoriteDao.delete(
            ShowFavoriteEntity(
                id= show.id,
                show= show.show
            )
        )
    }

    override suspend fun deleteShowFromFavoriteIfExist(show: Show) = withContext(IO){
        showFavoriteDao.deleteShowFromFavoriteIfExist(show)
    }

    override suspend fun addShowToFavoriteIfNotExist(show: Show) = withContext(IO){
        showFavoriteDao.addShowToFavoriteIfNotExist(show)
    }

    override suspend fun checkIfItIsFavorite(show: Show): Boolean = withContext(IO) {
        showFavoriteDao.checkIfItIsFavorite(show)
    }
}