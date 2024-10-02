package com.sb.moovich.presentation.all.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.repository.MovieRepository
import com.sb.moovich.presentation.all.ui.model.AllMoviesFragmentEvent
import com.sb.moovich.presentation.all.ui.model.AllMoviesFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val navigation: INavigation,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AllMoviesFragmentState>(AllMoviesFragmentState.Loading)
    val state = _state.asStateFlow()

    fun initMovies(type: GetAllType) {
        launch {
            movieRepository.getAllMovies(type).collect { list ->
                _state.update {
                    val movies =
                        if (it is AllMoviesFragmentState.Movies) it.movieList + list else list
                    AllMoviesFragmentState.Movies(
                        movieList = movies,
                        lastPage = list.isEmpty()
                    )
                }
            }
        }
    }

    fun fetchEvent(event: AllMoviesFragmentEvent) {
        when (event) {
            is AllMoviesFragmentEvent.OnMovieClick -> navigation.navigateToMovie(event.movie.id)
        }
    }

    fun nextPage() {
        launch {
            movieRepository.movieNextPage()
        }
    }
}