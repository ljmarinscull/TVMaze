package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.api.model.EpisodeJO
import com.example.tvmaze.data.api.model.getPoster
import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.utils.validValue
import javax.inject.Inject

/**
 * A mapper to convert EpisodeJO to Episode.
 */
class EpisodeMapper @Inject constructor(): IMapperOneWay<EpisodeJO, Episode> {

    override fun map(input: EpisodeJO): Episode {
        return Episode(
                id = input.id,
                name = input.name.validValue(),
                number = input.number.validValue(),
                season = input.season.validValue(),
                image = input.image.getPoster(),
                summary = input.summary.validValue()
        )
    }
}