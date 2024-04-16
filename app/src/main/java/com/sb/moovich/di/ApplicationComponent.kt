package com.sb.moovich.di

import android.app.Application
import com.sb.moovich.presentation.home.HomeFragment
import com.sb.moovich.presentation.home.movie_info.MovieInfoFragment
import com.sb.moovich.presentation.search.SearchFragment
import com.sb.moovich.presentation.search.SearchViewModel
import com.sb.moovich.presentation.watch_list.WatchListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: MovieInfoFragment)
    fun inject(fragment: WatchListFragment)
    fun inject(fragment: SearchFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent
    }
}