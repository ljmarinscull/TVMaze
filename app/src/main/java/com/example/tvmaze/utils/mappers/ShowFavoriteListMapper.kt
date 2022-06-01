package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.database.ShowFavoriteEntity
import com.example.tvmaze.data.model.ShowFavorite
import javax.inject.Inject

class ShowFavoriteListMapper @Inject constructor(
    private val mapperShowFavorite: IMapper<ShowFavorite, ShowFavoriteEntity>
) : IListMapper<ShowFavorite, ShowFavoriteEntity> {
    override fun map(input: List<ShowFavorite>): List<ShowFavoriteEntity> {
        return input.map { mapperShowFavorite.map(it) }
    }

    override fun mapReverse(input: List<ShowFavoriteEntity>): List<ShowFavorite> {
        return input.map { mapperShowFavorite.mapReverse(it) }
    }
}