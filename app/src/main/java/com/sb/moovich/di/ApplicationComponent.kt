package com.sb.moovich.di

import com.sb.moovich.presentation.home.HomeFragment
import com.sb.moovich.presentation.home.movie_info.MovieInfoFragment
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: MovieInfoFragment)

    @Component.Factory
    interface Factory {
        fun create() : ApplicationComponent
    }
}