package com.example.skuska.database

import androidx.lifecycle.LiveData
import com.example.skuska.model.Memory

class LocalMemoryRepositoryImpl(private val dao:MemoryDao): ILocalMemoryRepository {
    override fun getAll(): LiveData<MutableList<Memory>> {
        return dao.getAll()
    }

    override suspend fun findById(id: Long): Memory {
        return dao.findById(id)
    }

    override suspend fun insertMemory(memory: Memory): Long {
        return dao.insertMemory(memory)
    }

    override suspend fun updateMemory(memory: Memory) {
        dao.updateMemory(memory)
    }

    override suspend fun deleteMemory(memory: Memory) {
        dao.deleteMemory(memory)
    }
}