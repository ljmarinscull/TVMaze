package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.api.model.ShowSearchedJO
import com.example.tvmaze.data.model.Show
import javax.inject.Inject

class ShowSearchedListMapper @Inject constructor(
    private val mapperShowSearched: IMapperOneWay<ShowSearchedJO, Show>
) : IListMapperOneWay<ShowSearchedJO, Show> {
    override fun map(input: List<ShowSearchedJO>): List<Show> {
        return input.map { mapperShowSearched.map(it) }
    }
}