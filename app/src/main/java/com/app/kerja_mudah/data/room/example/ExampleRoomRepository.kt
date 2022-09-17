package com.app.kerja_mudah.data.room.example

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleRoomRepository @Inject constructor(
    var context: Context
) {
    companion object{
        var instance:ExampleDatabase ?= null
    }

    init {
        instance = ExampleDatabase.getDataBase(context)
    }

    suspend fun getAll() = instance?.exampleDao()?.getAllExample()

    suspend fun insertData(exampleEntity: ExampleEntity) = instance?.exampleDao()?.insert(exampleEntity)
}