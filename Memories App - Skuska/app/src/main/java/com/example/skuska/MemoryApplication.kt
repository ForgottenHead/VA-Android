package com.example.skuska

import android.app.Application
import com.example.skuska.di.daoModule
import com.example.skuska.di.databaseModule
import com.example.skuska.di.repositoryModule
import com.example.skuska.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MemoryApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(applicationContext)
            // vypisat vsetky moduly
            modules(databaseModule, daoModule, repositoryModule, viewModelModule)
        }
    }
}