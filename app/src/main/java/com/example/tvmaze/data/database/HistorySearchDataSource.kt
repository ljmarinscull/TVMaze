package com.example.tvmaze.data.database

import com.example.tvmaze.data.database.dao.HistorySearchDao
import com.example.tvmaze.data.model.HistorySearch
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.mappers.HistorySearchListMapper
import com.example.tvmaze.utils.mappers.HistorySearchMapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistorySearchDataSource @Inject constructor(
    private val historySearchListMapper: HistorySearchListMapper,
    private val historySearchMapper: HistorySearchMapper,
    private val historySearchDao: HistorySearchDao,
) : IHistorySearchDataSource {

    override suspend fun getRecentSearches(): Resource<List<HistorySearch>> = withContext(IO){
        return@withContext Resource.Success(
                historySearchListMapper.mapReverse(historySearchDao.getAll())
        )
    }

    override suspend fun addRecentSearch(search: HistorySearch) = withContext(IO) {
         val result = historySearchDao.insertOrUpdateHistorySearch(search.id, search.search)
    }

    override suspend fun deleteRecentSearch(search: HistorySearch) = withContext(IO){
        historySearchDao.delete(historySearchMapper.map(search))
    }
}