package com.example.tvmaze.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_searches")
data class HistorySearchEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "_id")
    var id: Long = 0,

    val search: String
)