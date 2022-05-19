package com.example.skuska.di

import com.example.skuska.database.ILocalMemoryRepository
import com.example.skuska.database.LocalMemoryRepositoryImpl
import com.example.skuska.database.MemoryDao
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRepository(get()) }
}

fun provideRepository(memoryDao: MemoryDao): ILocalMemoryRepository = LocalMemoryRepositoryImpl(memoryDao)