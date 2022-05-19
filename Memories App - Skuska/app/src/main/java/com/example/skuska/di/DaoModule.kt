package com.example.skuska.di

import com.example.skuska.database.MemoryDao
import com.example.skuska.database.MemoryDatabase
import org.koin.dsl.module

val daoModule = module {
    single { provideDao(get()) }
}

fun provideDao(database: MemoryDatabase): MemoryDao = database.memoryDao()