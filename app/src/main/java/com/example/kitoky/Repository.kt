package com.example.kitoky

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.kitoky.Dao.VideoDao
import com.example.kitoky.Entity.Data
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class Repository (private val videoDao: VideoDao)  {
    val readAllData: LiveData<List<Data>> = videoDao.readAllData()


    suspend fun insertVideo(video:Data){
        videoDao.insertNotes(video)
    }

    suspend fun deleteVideo(video:Data){
        videoDao.deleteVideos(video)
    }

    suspend fun deleteSpecificVideo(id: Int){
        videoDao.deleteSpecificVideo(id)
    }
    suspend fun getAllVideos(): List<Data>{
        return videoDao.getAllVideos()
    }


}


