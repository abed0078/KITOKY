package com.example.kitoky.Dao

import androidx.lifecycle.LiveData
import com.example.kitoky.Entity.Data

import androidx.room.*


@Dao
interface VideoDao {
    @Query("SELECT * from addVideo ORDER BY id DESC")
    fun readAllData(): LiveData<List<Data>>

    @Query("SELECT * FROM addVideo  ORDER BY id DESC ")
   suspend fun getAllVideos(): List<Data>

    @Query("SELECT * FROM addVideo  WHERE id=:id ")
    fun getSpecificNotes(id: Int): Data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertNotes(video: Data)

    @Delete
    suspend fun deleteVideos(video: Data)

    @Query("DELETE FROM addVideo WHERE id=:id ")
    fun deleteSpecificVideo(id: Int)





}
