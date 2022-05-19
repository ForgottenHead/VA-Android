package cz.mendelu.tododolist.di

import cz.mendelu.tododolist.AddTaskViewModel
import cz.mendelu.tododolist.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =  module {

    viewModel {
        TaskListViewModel(get())
    }

    viewModel{
        AddTaskViewModel(get())
    }
}