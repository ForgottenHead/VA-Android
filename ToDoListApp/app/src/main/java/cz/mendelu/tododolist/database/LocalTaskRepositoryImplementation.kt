package cz.mendelu.tododolist.database

import androidx.lifecycle.LiveData
import cz.mendelu.tododolist.model.Task

class LocalTaskRepositoryImplementation(private val dao:TasksDao): ILocalTaskRepository {


    override fun getAll(): LiveData<MutableList<Task>> {
        return dao.getAll()
    }

    override suspend fun findById(id: Long): Task {
        return dao.findById(id)
    }

    override suspend fun insertTask(task: Task): Long {
        return dao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    override suspend fun setCheckBoxState(id: Long, state: Boolean) {
        dao.setCheckBoxState(id, state)
    }
}