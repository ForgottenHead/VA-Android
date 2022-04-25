package cz.mendelu.tododolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import cz.mendelu.tododolist.model.Task

@Dao
interface TasksDao {

    @Query("Select * FROM tasks")
    fun getAll(): LiveData<MutableList<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun findById(id: Long): Task

    @Insert
    fun insertTask(task: Task): Long

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}