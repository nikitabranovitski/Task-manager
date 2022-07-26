package com.branovitski.taskmanager.di

import android.app.Application
import com.branovitski.taskmanager.database.TaskManagerDAO
import com.branovitski.taskmanager.database.TaskManagerDB
import com.github.terrakok.cicerone.BaseRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Singleton
    @Provides
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Singleton
    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }
}