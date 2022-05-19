package com.example.skuska.di

import android.content.Context
import com.example.skuska.database.MemoryDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideDatabase(context: Context): MemoryDatabase = MemoryDatabase.getDatabase(context)