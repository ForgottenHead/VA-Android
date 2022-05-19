package com.example.skuska.di

import com.example.skuska.viewModels.AddMemoryViewModel
import com.example.skuska.viewModels.EditMemoryViewModel
import com.example.skuska.viewModels.MemoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =  module {

    viewModel {
        MemoryListViewModel(get())
    }

    viewModel{
        AddMemoryViewModel(get())
    }

    viewModel{
        EditMemoryViewModel(get())
    }
}