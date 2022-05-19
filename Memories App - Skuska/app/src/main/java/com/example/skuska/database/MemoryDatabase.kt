package com.example.skuska.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.skuska.model.Memory

@Database(entities = [Memory::class], version = 2, exportSchema = true)
abstract class MemoryDatabase: RoomDatabase() {

    abstract fun memoryDao():MemoryDao

    companion object {
        private var INSTANCE: MemoryDatabase? = null
        fun getDatabase(context: Context): MemoryDatabase {
            if (INSTANCE == null) {
                synchronized(MemoryDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MemoryDatabase::class.java, "memory_database"
                        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }



    }

}