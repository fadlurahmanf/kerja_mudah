package com.app.kerja_mudah.data.room.example

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "example_table")
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int?=null,
    @ColumnInfo(name = "text") var text:String?=null
)
