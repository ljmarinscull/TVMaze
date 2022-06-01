package com.example.tvmaze.data.repository

import com.example.tvmaze.data.api.IShowDataSource
import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.utils.Resource
import javax.inject.Inject

class ShowRepository @Inject constructor(private val dataSource: IShowDataSource) : IShowRepository {
    override suspend fun getShows(page: Int): Resource<List<Show>> = dataSource.getShows(page)
    override suspend fun getShowByName(name: String): Resource<List<Show>> = dataSource.getShowByName(name)
    override suspend fun getShowsEpisodes(showId: Int): Resource<List<Episode>> = dataSource.getShowsEpisodes(showId)
}