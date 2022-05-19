package com.example.skuska.viewModels

import androidx.lifecycle.ViewModel
import com.example.skuska.database.ILocalMemoryRepository
import com.example.skuska.model.Memory

class EditMemoryViewModel(private val repository: ILocalMemoryRepository): ViewModel() {
    var id: Long? = null
    var memory: Memory = (Memory("", "", "", null))


    suspend fun findMemoryById():Memory{
        return repository.findById(id!!)
    }

    suspend fun deleteMemory(){
        repository.deleteMemory(memory)
    }

}


