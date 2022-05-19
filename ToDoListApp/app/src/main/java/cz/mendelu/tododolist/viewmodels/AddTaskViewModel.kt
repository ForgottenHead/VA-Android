package cz.mendelu.tododolist.viewmodels

import androidx.lifecycle.ViewModel
import cz.mendelu.tododolist.database.ILocalTaskRepository
import cz.mendelu.tododolist.model.Task

class AddTaskViewModel(private val repository: ILocalTaskRepository) : ViewModel() {

    var id: Long? = null
    var task: Task = Task("")

    suspend fun saveTask(){
        if (id == null){
            repository.insertTask(task)
        }else{
            repository.updateTask(task)
        }
    }

    suspend fun findTaskById():Task{
        return repository.findById(id!!)
    }
}