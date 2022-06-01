package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.database.HistorySearchEntity
import com.example.tvmaze.data.model.HistorySearch
import javax.inject.Inject

class HistorySearchListMapper @Inject constructor(
private val mapperHistorySearch: IMapper<HistorySearch, HistorySearchEntity>
) : IListMapper<HistorySearch, HistorySearchEntity> {
    override fun map(input: List<HistorySearch>): List<HistorySearchEntity> {
        return input.map { mapperHistorySearch.map(it) }
    }

    override fun mapReverse(input: List<HistorySearchEntity>): List<HistorySearch> {
        return input.map { mapperHistorySearch.mapReverse(it) }
    }
}