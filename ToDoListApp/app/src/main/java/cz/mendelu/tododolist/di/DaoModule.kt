package cz.mendelu.tododolist.di

import cz.mendelu.tododolist.database.TasksDao
import cz.mendelu.tododolist.database.TasksDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val daoModule = module {
    single { provideDao(get()) }
}

fun provideDao(database: TasksDatabase): TasksDao = database.tasksDao()