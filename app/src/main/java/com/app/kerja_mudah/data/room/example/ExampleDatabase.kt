package com.app.kerja_mudah.data.room.example

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExampleEntity::class], version = ExampleDatabase.VERSION )
abstract class ExampleDatabase:RoomDatabase() {

    abstract fun exampleDao():ExampleDao

    companion object{
        const val VERSION = 1
        @Volatile
        private var INSTANCE:ExampleDatabase ?= null
        fun getDataBase(context: Context):ExampleDatabase{
            return INSTANCE ?: synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExampleDatabase::class.java,
                    "example_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}