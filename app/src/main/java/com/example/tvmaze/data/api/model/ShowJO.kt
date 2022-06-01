package com.example.tvmaze.data.api.model

data class ShowJO(
    val id: Int,
    val name: String?,
    val image: ShowImageJO?,
    val schedule: ShowScheduleJO?,
    val genres: List<String>?,
    val summary: String?
)
