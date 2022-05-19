package com.example.skuska.viewModels

import androidx.lifecycle.ViewModel
import com.example.skuska.database.ILocalMemoryRepository
import com.example.skuska.model.Memory

class AddMemoryViewModel(private val repository: ILocalMemoryRepository) : ViewModel(){
    var memory: Memory = (Memory("", "", "", null))

    suspend fun saveMemory(){
        repository.insertMemory(memory)
    }
}