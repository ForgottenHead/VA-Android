package com.example.skuska.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skuska.model.Memory

interface ILocalMemoryRepository {
    fun getAll(): LiveData<MutableList<Memory>>
    suspend fun findById(id: Long): Memory
    suspend fun insertMemory(memory: Memory): Long
    suspend fun updateMemory(memory: Memory)
    suspend fun deleteMemory(memory: Memory)

}