package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.database.HistorySearchEntity
import com.example.tvmaze.data.model.HistorySearch
import javax.inject.Inject

class HistorySearchMapper @Inject constructor() : IMapper<HistorySearch, HistorySearchEntity> {

    override fun map(input: HistorySearch): HistorySearchEntity {
        return HistorySearchEntity(
            id = input.id,
            search = input.search
        )
    }

    override fun mapReverse(input: HistorySearchEntity): HistorySearch {
        return HistorySearch(
            id = input.id,
            search = input.search
        )
    }
}