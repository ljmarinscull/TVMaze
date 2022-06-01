package com.example.tvmaze.data.api.model

data class EpisodeJO(
   val id: Int,
   val name: String?,
   val number: Int?,
   val season: Int?,
   val summary: String?,
   val image: ShowImageJO?
)
