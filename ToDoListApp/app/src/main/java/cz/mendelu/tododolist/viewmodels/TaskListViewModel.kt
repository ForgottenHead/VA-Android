package cz.mendelu.tododolist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.mendelu.tododolist.database.ILocalTaskRepository
import cz.mendelu.tododolist.model.Task


class TaskListViewModel(private val tasksRepository: ILocalTaskRepository) : ViewModel() {


    fun getAll(): LiveData<MutableList<Task>> {
        return tasksRepository.getAll()
    }

    suspend fun setCheckBoxState(id: Long, state: Boolean) = tasksRepository.setCheckBoxState(id, state)
}