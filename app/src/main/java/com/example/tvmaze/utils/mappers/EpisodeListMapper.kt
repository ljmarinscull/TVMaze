package com.example.tvmaze.utils.mappers

import com.example.tvmaze.data.api.model.EpisodeJO
import com.example.tvmaze.data.model.Episode
import javax.inject.Inject

class EpisodeListMapper @Inject constructor(
    private val mapperEpisode: IMapperOneWay<EpisodeJO, Episode>
) : IListMapperOneWay<EpisodeJO, Episode> {
    override fun map(input: List<EpisodeJO>): List<Episode> {
        return input.map { mapperEpisode.map(it) }
    }
}