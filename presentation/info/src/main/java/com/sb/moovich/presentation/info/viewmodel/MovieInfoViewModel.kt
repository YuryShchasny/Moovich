package com.sb.moovich.presentation.info.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.usecases.movie.GetMovieByIdUseCase
import com.sb.moovich.domain.usecases.recent.AddMovieToRecentUseCase
import com.sb.moovich.domain.usecases.watch.AddMovieToWatchListUseCase
import com.sb.moovich.domain.usecases.watch.DeleteMovieFromWatchListUseCase
import com.sb.moovich.domain.usecases.watch.GetWatchMovieByIdUseCase
import com.sb.moovich.presentation.info.ui.model.MovieInfoFragmentEvent
import com.sb.moovich.presentation.info.ui.model.MovieInfoFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val navigation: INavigation,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    private val deleteMovieFromWatchListUseCase: DeleteMovieFromWatchListUseCase,
    private val getWatchMovieByIdUseCase: GetWatchMovieByIdUseCase,
    private val addMovieToRecentUseCase: AddMovieToRecentUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<MovieInfoFragmentState>(MovieInfoFragmentState.Loading)
    val state = _state.asStateFlow()

    fun getMovieById(movieId: Int) {
        launch {
            getMovieByIdUseCase(movieId)?.let { movieInfo ->
                val filteredMovie =
                    movieInfo.copy(
                        actors = movieInfo.actors.filter { it.name.isNotEmpty() },
                        similarMovies = movieInfo.similarMovies.filter { it.name.isNotEmpty() },
                    )
                val bookMarkChecked = getWatchMovieByIdUseCase(filteredMovie.id) != null
                _state.value = MovieInfoFragmentState.Content(
                    movie = filteredMovie,
                    bookMarkChecked = bookMarkChecked
                )
                addMovieToRecentUseCase(filteredMovie)
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
                    if(state.bookMarkChecked) deleteMovieFromWatchListUseCase(it.movie.id)
                    else addMovieToWatchListUseCase(it.movie)
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
