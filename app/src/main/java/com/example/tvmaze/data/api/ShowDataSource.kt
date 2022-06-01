package com.example.tvmaze.data.api

import com.example.tvmaze.data.api.model.EpisodeJO
import com.example.tvmaze.data.api.model.ShowJO
import com.example.tvmaze.data.api.model.ShowSearchedJO
import com.example.tvmaze.data.model.Episode
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.utils.Resource
import com.example.tvmaze.utils.mappers.IListMapper
import com.example.tvmaze.utils.mappers.IListMapperOneWay
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowDataSource@Inject constructor(
    private val showMapperList: IListMapper<ShowJO, Show>,
    private val showSearchedListMapper: IListMapperOneWay<ShowSearchedJO, Show>,
    private val episodeMapperList: IListMapperOneWay<EpisodeJO, Episode>,
    private val api: ApiService
) : IShowDataSource {

    override suspend fun getShows(page: Int): Resource<List<Show>> = withContext(IO) {
        val result = api.getShows(page).execute()
        val data = result.body()
        if(data.isNullOrEmpty())
            return@withContext Resource.Success(listOf<Show>())

        val list = showMapperList.map(data)
        return@withContext Resource.Success(list)
    }

    override suspend fun getShowsEpisodes(showId: Int): Resource<List<Episode>> = withContext(IO) {
        val result = api.getShowsEpisodes(showId).execute()
        val data = result.body()
        if(data.isNullOrEmpty())
            return@withContext Resource.Success(listOf<Episode>())

        val list = episodeMapperList.map(data)
        return@withContext Resource.Success(list)
    }

    override suspend fun getShowByName(name: String): Resource<List<Show>> = withContext(IO) {
        val result = api.getShowByName(name).execute()
        val data = result.body()
        if(data.isNullOrEmpty())
            return@withContext Resource.Success(listOf<Show>())

        val list = showSearchedListMapper.map(data)
        return@withContext Resource.Success(list)
    }

}