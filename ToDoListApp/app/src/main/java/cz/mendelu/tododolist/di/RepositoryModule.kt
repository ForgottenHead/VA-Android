package cz.mendelu.tododolist.di

import android.content.Context
import cz.mendelu.tododolist.database.ILocalTaskRepository
import cz.mendelu.tododolist.database.LocalTaskRepositoryImplementation
import cz.mendelu.tododolist.database.TasksDao
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRepository(get()) }
}

fun provideRepository(taskDao: TasksDao): ILocalTaskRepository = LocalTaskRepositoryImplementation(taskDao)