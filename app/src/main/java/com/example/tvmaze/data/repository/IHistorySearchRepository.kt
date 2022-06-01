package com.example.tvmaze.data.repository

import com.example.tvmaze.data.model.HistorySearch
import com.example.tvmaze.utils.Resource

interface IHistorySearchRepository {
    suspend fun getRecentSearches(): Resource<List<HistorySearch>>
    suspend fun deleteRecentSearch(search: HistorySearch)
    suspend fun addRecentSearch(search: HistorySearch)
}