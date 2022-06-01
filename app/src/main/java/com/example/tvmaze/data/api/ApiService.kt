package com.example.tvmaze.data.api

import com.example.tvmaze.data.api.model.EpisodeJO
import com.example.tvmaze.data.api.model.ShowJO
import com.example.tvmaze.data.api.model.ShowSearchedJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/shows")
    fun getShows(@Query("page") page: Int): Call<List<ShowJO>>

    @GET("/search/shows")
    fun getShowByName(@Query("q") name: String): Call<List<ShowSearchedJO>>

    @GET("/shows/{showId}/episodes")
    fun getShowsEpisodes(@Path("showId") showId: Int): Call<List<EpisodeJO>>
}