package com.branovitski.taskmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.branovitski.taskmanager.model.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class TaskManagerDB : RoomDatabase() {

    abstract fun taskManagerDao(): TaskManagerDAO

    companion object {
        private lateinit var database: TaskManagerDB

        fun initDatabase(context: Context) {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(context, TaskManagerDB::class.java, "notes_table")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}