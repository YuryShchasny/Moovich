package com.sb.moovich.di

import androidx.lifecycle.ViewModel
import com.sb.moovich.presentation.home.HomeViewModel
import com.sb.moovich.presentation.home.movie_info.MovieInfoViewModel
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

}