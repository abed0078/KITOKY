package com.example.kitoky

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kitoky.Database.videoDatabase
import com.example.kitoky.Entity.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Data>>
    private val repository: Repository


    init {

        val videoDao = videoDatabase.getInstance(application).videoDao()
        repository = Repository(videoDao)
        readAllData = repository.readAllData
    }

    fun insertVideo(video: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertVideo(video)
        }

    }

    fun deleteVideo(video: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteVideo(video)

        }

    }

    fun deleteSpecificVideo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSpecificVideo(id)
        }

    }

    fun getAllVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllVideos()
        }

    }


}