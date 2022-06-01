package com.example.tvmaze.data.database

import com.example.tvmaze.data.model.HistorySearch
import com.example.tvmaze.utils.Resource

interface IHistorySearchDataSource {
    suspend fun getRecentSearches(): Resource<List<HistorySearch>>
    suspend fun deleteRecentSearch(search: HistorySearch)
    suspend fun addRecentSearch(search: HistorySearch)
}