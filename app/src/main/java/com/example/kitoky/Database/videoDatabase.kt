package com.example.kitoky.Database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kitoky.Dao.VideoDao

import com.example.kitoky.Entity.Data




    @Database(entities = [Data::class], version = 2, exportSchema = false)
    abstract class videoDatabase : RoomDatabase() {

        companion object {
            @Volatile
            private var INSTANCE: videoDatabase? = null

            fun getInstance(context: Context): videoDatabase {
                synchronized(this) {
                    var instance = INSTANCE

                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            videoDatabase::class.java,
                            "sleep_history_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                        INSTANCE = instance
                    }
                    return instance
                }
            }


        }

        abstract fun videoDao(): VideoDao


    }



