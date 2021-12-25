package com.example.kitoky.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="addVideo")
class Data:Serializable{
    @PrimaryKey(autoGenerate=true)
    var id:Int?=null

    @ColumnInfo(name="TextViewTitle")
    var Title:String?=null

   // @ColumnInfo(name="TextViewDesc")
   // var Desc:String?=null

    @ColumnInfo(name="Video")
    var video:String?=null
}
