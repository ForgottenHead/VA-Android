package com.example.skuska.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skuska.database.ILocalMemoryRepository
import com.example.skuska.model.Memory

class MemoryListViewModel(private val memoryRepository: ILocalMemoryRepository): ViewModel() {

    fun getAll(): LiveData<MutableList<Memory>> {
        return memoryRepository.getAll()
    }

}