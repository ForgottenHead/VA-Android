package cz.mendelu.tododolist.di

import android.content.Context
import cz.mendelu.tododolist.database.TasksDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
}

fun provideDatabase(context: Context): TasksDatabase = TasksDatabase.getDatabase(context)
