package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.api.model.ShowJO
import com.example.tvmaze.data.model.Show
import javax.inject.Inject

class ShowListMapper @Inject constructor(
    private val mapperShow: IMapper<ShowJO, Show>
) : IListMapper<ShowJO, Show> {
    override fun map(input: List<ShowJO>): List<Show> {
        return input.map { mapperShow.map(it) }
    }

    override fun mapReverse(input: List<Show>): List<ShowJO> {
        return input.map { mapperShow.mapReverse(it) }
    }
}