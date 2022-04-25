package cz.mendelu.tododolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.mendelu.tododolist.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class TasksDatabase : RoomDatabase(){

    abstract fun tasksDao():TasksDao

    companion object {
        private var INSTANCE: TasksDatabase? = null
        fun getDatabase(context: Context): TasksDatabase {
            if (INSTANCE == null) {
                synchronized(TasksDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TasksDatabase::class.java, "tasks_database"
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}