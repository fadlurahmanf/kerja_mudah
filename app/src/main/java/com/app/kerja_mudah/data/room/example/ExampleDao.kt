package com.app.kerja_mudah.data.room.example

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExampleDao {

    @Query("SELECT * FROM example_table")
    suspend fun getAllExample() : List<ExampleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(example:ExampleEntity)
}