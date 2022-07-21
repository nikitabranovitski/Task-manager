package com.branovitski.taskmanager.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.branovitski.taskmanager.database.TaskManagerDAO
import com.branovitski.taskmanager.database.TaskManagerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getDatabase(context: Application): TaskManagerDB{
        return TaskManagerDB.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getDao(app: TaskManagerDB): TaskManagerDAO{
        return app.taskManagerDao()
    }
}