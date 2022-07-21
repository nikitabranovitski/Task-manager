package com.branovitski.taskmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.branovitski.taskmanager.model.Notes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class TaskManagerDB : RoomDatabase() {

    abstract fun taskManagerDao(): TaskManagerDAO

    companion object {

        fun getDatabase(context: Context): TaskManagerDB {
            return Room.databaseBuilder<TaskManagerDB>(
                context.applicationContext,
                TaskManagerDB::class.java,
                "notes_table"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}