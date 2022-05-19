package com.example.skuska.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skuska.model.Memory

@Dao
interface MemoryDao {
    @Query("Select * FROM memories")
    fun getAll(): LiveData<MutableList<Memory>>

    @Query("SELECT * FROM memories WHERE id = :id")
    suspend fun findById(id: Long): Memory

    @Insert
    suspend fun insertMemory(memory: Memory): Long

    @Update
    suspend fun updateMemory(memory: Memory)

    @Delete
    suspend fun deleteMemory(memory: Memory)
}