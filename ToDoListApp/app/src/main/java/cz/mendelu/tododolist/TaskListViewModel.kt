package cz.mendelu.tododolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.tododolist.database.ILocalTaskRepository
import cz.mendelu.tododolist.model.Task


class TaskListViewModel(private val tasksRepository: ILocalTaskRepository) : ViewModel() {


    fun getAll(): LiveData<MutableList<Task>> {
        return tasksRepository.getAll()
    }
}