package com.example.tvmaze.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    val id: Int,
    var favorite: Boolean = false,
    val name: String,
    val poster: String?,
    val time: String,
    val days: String,
    val genres: String,
    val summary: String,
) : Parcelable
