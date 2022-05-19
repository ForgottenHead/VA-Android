package cz.mendelu.tododolist.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cz.mendelu.tododolist.model.Task

interface ILocalTaskRepository {
    fun getAll(): LiveData<MutableList<Task>>
    suspend fun findById(id: Long): Task
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun setCheckBoxState(id: Long, state: Boolean)
}