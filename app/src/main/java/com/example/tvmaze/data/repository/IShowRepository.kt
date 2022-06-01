package com.example.tvmaze.data.repository

import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.utils.Resource

interface IShowRepository {
    suspend fun getShows(page: Int) : Resource<List<Show>>
    suspend fun getShowByName(name: String): Resource<List<Show>>
    suspend fun getShowsEpisodes(showId: Int) : Resource<List<Episode>>
}