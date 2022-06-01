package com.example.tvmaze.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val number: Int,
    val season: Int,
    val summary: String,
    val image: String?
) : Parcelable
