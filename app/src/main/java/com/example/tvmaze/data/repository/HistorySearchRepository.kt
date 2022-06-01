package com.example.tvmaze.data.repository

import com.example.tvmaze.data.database.IHistorySearchDataSource
import com.example.tvmaze.data.model.HistorySearch
import javax.inject.Inject

class HistorySearchRepository @Inject constructor(
    private val dataSource: IHistorySearchDataSource
): IHistorySearchRepository {

    override suspend fun getRecentSearches() = dataSource.getRecentSearches()
    override suspend fun deleteRecentSearch(search: HistorySearch) = dataSource.deleteRecentSearch(search)
    override suspend fun addRecentSearch(search: HistorySearch) = dataSource.addRecentSearch(search)
}