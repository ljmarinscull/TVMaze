package com.example.tvmaze.data.database.dao

import androidx.room.*
import com.example.tvmaze.data.database.ShowFavoriteEntity
import com.example.tvmaze.data.model.Show

@Dao
interface ShowFavoriteDao {

    @Query("SELECT * FROM favorite_shows")
    fun getAll(): List<ShowFavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(show: ShowFavoriteEntity): Long

    @Query("DELETE FROM favorite_shows")
    fun deleteAll()

    @Transaction
    suspend fun deleteShowFromFavoriteIfExist(show: Show){
        try {
            val result = getAll()
            for(item in result){
                if (item.show.id == show.id){
                    delete(item)
                    return
                }
            }
        } catch (e: Exception){
            println(e.localizedMessage)
        }
    }

    @Transaction
    suspend fun addShowToFavoriteIfNotExist(show: Show){
        try {
            val result = getAll()
            for(item in result){
                if (item.show.id == show.id){
                    return
                }
            }

            insert(
                ShowFavoriteEntity(0, show)
            )
        } catch (e: Exception){
            println(e.localizedMessage)
        }
    }

    @Transaction
    suspend fun checkIfItIsFavorite(show: Show): Boolean {
        try {
            val result = getAll()
            for(item in result){
                if (item.show.id == show.id){
                    return true
                }
            }

            return false
        } catch (e: Exception){
            println(e.localizedMessage)
            return false
        }
    }

    @Delete
    suspend fun delete(show: ShowFavoriteEntity)
}