package com.example.tvmaze.data.database.dao

import androidx.room.*
import com.example.tvmaze.data.database.HistorySearchEntity

@Dao
interface HistorySearchDao {

    @Query("SELECT * FROM history_searches")
    fun getAll(): List<HistorySearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historySearch: HistorySearchEntity): Long

    @Transaction
    suspend fun insertOrUpdateHistorySearch(searchId: Long, search: String){
        try {
            val result = findByHistorySearch(search)
            if (result != null){
                return
            } else {
                val newSearch = HistorySearchEntity(0, search)
                insert(newSearch)
            }
        } catch (e: Exception){
            e.printStackTrace()
            return
        }
    }

    @Query("SELECT * FROM history_searches WHERE search = :search")
    suspend fun findByHistorySearch(search: String): HistorySearchEntity?

    @Delete
    fun delete(show: HistorySearchEntity)
}