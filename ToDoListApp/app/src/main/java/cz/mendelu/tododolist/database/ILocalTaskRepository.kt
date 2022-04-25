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
    fun insertTask(task: Task): Long
    fun updateTask(task: Task)
    fun deleteTask(task: Task)
}