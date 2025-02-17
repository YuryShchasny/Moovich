package com.sb.moovich.di

import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.navigation.Navigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun bindFNavigation(navigation: Navigation): INavigation
}