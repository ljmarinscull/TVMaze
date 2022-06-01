package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.database.ShowFavoriteEntity
import com.example.tvmaze.data.model.ShowFavorite
import javax.inject.Inject

/**
 * A mapper to convert ShowFavorite to ShowFavoriteEntity and reverse.
 */
class ShowFavoriteMapper @Inject constructor(): IMapper<ShowFavorite, ShowFavoriteEntity> {

    override fun map(input: ShowFavorite): ShowFavoriteEntity {
        return ShowFavoriteEntity(
                id = input.id,
                show = input.show
        )
    }

    override fun mapReverse(input: ShowFavoriteEntity): ShowFavorite {
        return ShowFavorite(
            id = input.id,
            show = input.show
        )
    }
}