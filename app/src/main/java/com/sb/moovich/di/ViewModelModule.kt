package com.sb.moovich.di

import androidx.lifecycle.ViewModel
import com.sb.moovich.presentation.home.HomeViewModel
import com.sb.moovich.presentation.movie_info.MovieInfoViewModel
import com.sb.moovich.presentation.search.SearchViewModel
import com.sb.moovich.presentation.watch_list.WatchListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel : HomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieInfoViewModel::class)
    fun bindMovieInfoViewModel(viewModel : MovieInfoViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WatchListViewModel::class)
    fun bindWatchListViewModel(viewModel : WatchListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel : SearchViewModel) : ViewModel
}