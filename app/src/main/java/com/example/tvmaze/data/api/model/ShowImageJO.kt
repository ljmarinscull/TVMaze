package com.example.tvmaze.data.api.model

data class ShowImageJO(
    val medium: String?,
    val original: String?
)

fun ShowImageJO?.getPoster() : String? {
    if (this == null)
        return null

    this.apply {
        return if (medium != null)
            medium
        else
            original
    }

}