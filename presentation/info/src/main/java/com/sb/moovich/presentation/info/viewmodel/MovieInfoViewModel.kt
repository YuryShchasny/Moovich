package com.sb.moovich.presentation.info.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.exceptions.ResponseExceptions
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.repository.MovieRepository
import com.sb.moovich.domain.repository.SearchRepository
import com.sb.moovich.domain.repository.WatchMovieRepository
import com.sb.moovich.presentation.info.ui.model.MovieInfoFragmentEvent
import com.sb.moovich.presentation.info.ui.model.MovieInfoFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val navigation: INavigation,
    private val movieRepository: MovieRepository,
    private val watchMovieRepository: WatchMovieRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MovieInfoFragmentState>(MovieInfoFragmentState.Loading)
    val state = _state.asStateFlow()

    private val _error = MutableSharedFlow<ResponseExceptions>()
    val error = _error.asSharedFlow()

    fun getMovieById(movieId: Int) {
        launch(_error) {
            movieRepository.getMovieById(movieId)?.let { movieInfo ->
                val filteredMovie =
                    movieInfo.copy(
                        actors = movieInfo.actors.filter { it.name.isNotEmpty() },
                        similarMovies = movieInfo.similarMovies.filter { it.name.isNotEmpty() },
                    )
                val bookMarkChecked = watchMovieRepository.getWatchMovieById(filteredMovie.id) != null
                _state.value = MovieInfoFragmentState.Content(
                    movie = filteredMovie,
                    bookMarkChecked = bookMarkChecked
                )

                searchRepository.addMovieToRecentList(filteredMovie)
            }
        }
    }

    fun fetchEvent(event: MovieInfoFragmentEvent) {
        when(event) {
            MovieInfoFragmentEvent.OnBookmarkClick -> reverseBookmark()
            is MovieInfoFragmentEvent.OnSimilarMovieClick -> navigation.navigateToMovie(event.movie.id)
            MovieInfoFragmentEvent.SeeAllActors -> seeAll()
        }
    }

    private fun reverseBookmark() {
        _state.update { state ->
            (state as? MovieInfoFragmentState.Content)?.let {
                launch {
                    if(state.bookMarkChecked) watchMovieRepository.deleteMovieFromWatchList(it.movie.id)
                    else watchMovieRepository.addMovieToWatchList(it.movie)
                }
                state.copy(bookMarkChecked = !it.bookMarkChecked)
            } ?: state
        }
    }

    private fun seeAll() {
        _state.update {
            (it as MovieInfoFragmentState.Content).copy(
                seeAllActors = !it.seeAllActors
            )
        }
    }
}
