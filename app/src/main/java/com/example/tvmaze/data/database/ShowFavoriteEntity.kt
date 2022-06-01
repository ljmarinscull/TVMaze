package com.example.tvmaze.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tvmaze.data.model.Show

@Entity(tableName = "favorite_shows")
data class ShowFavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "_id")
    var id: Long = 0,

    @Embedded val show: Show
)