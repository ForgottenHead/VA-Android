package cz.mendelu.tododolist.di

import cz.mendelu.tododolist.viewmodels.AddTaskViewModel
import cz.mendelu.tododolist.viewmodels.MapsFragmentViewModel
import cz.mendelu.tododolist.viewmodels.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =  module {

    viewModel {
        TaskListViewModel(get())
    }

    viewModel{
        AddTaskViewModel(get())
    }

    viewModel{
        MapsFragmentViewModel()
    }
}