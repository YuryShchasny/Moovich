package com.sb.moovich.presentation.info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.usecases.GetMovieByIdUseCase
import com.sb.moovich.domain.usecases.recent.AddMovieToRecentUseCase
import com.sb.moovich.domain.usecases.watch.AddMovieToWatchListUseCase
import com.sb.moovich.domain.usecases.watch.DeleteMovieFromWatchListUseCase
import com.sb.moovich.domain.usecases.watch.GetWatchMovieByIdUseCase
import com.sb.moovich.presentation.info.ui.MovieInfoFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    private val deleteMovieFromWatchListUseCase: DeleteMovieFromWatchListUseCase,
    private val getWatchMovieByIdUseCase: GetWatchMovieByIdUseCase,
    private val addMovieToRecentUseCase: AddMovieToRecentUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<MovieInfoFragmentState>(MovieInfoFragmentState.Loading)
    val state = _state.asStateFlow()

    fun getMovieById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieByIdUseCase(movieId)?.let { movieInfo ->
                val filteredMovie =
                    movieInfo.copy(
                        actors = movieInfo.actors.filter { it.name.isNotEmpty() },
                        similarMovies = movieInfo.similarMovies.filter { it.name.isNotEmpty() },
                    )
                val bookMarkChecked = getWatchMovieByIdUseCase(filteredMovie.id) != null
                _state.value = MovieInfoFragmentState.Content(
                    currencyMovie = filteredMovie,
                    bookMarkChecked = bookMarkChecked
                )
                addMovieToRecentUseCase(filteredMovie)
            }
        }
    }

    fun reverseBookmarkValue() {
        _state.update { state ->
            (state as MovieInfoFragmentState.Content).let {
                viewModelScope.launch(Dispatchers.IO) {
                    if(it.bookMarkChecked) deleteMovieFromWatchListUseCase(it.currencyMovie.id)
                    else addMovieToWatchListUseCase(it.currencyMovie)
                }
                it.copy(bookMarkChecked = !it.bookMarkChecked)
            }
        }
    }

    fun seeAll() {
        _state.update {
            (it as MovieInfoFragmentState.Content).copy(
                seeAllActors = !it.seeAllActors
            )
        }
    }
}
