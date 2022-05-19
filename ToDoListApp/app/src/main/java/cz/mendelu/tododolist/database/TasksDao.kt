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
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET done = :state WHERE id = :id")
    suspend fun setCheckBoxState(id: Long, state: Boolean)
}