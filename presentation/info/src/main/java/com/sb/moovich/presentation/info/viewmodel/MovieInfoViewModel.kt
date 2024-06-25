package com.sb.moovich.presentation.info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.usecases.AddMovieToRecentUseCase
import com.sb.moovich.domain.usecases.AddMovieToWatchListUseCase
import com.sb.moovich.domain.usecases.DeleteMovieFromWatchListUseCase
import com.sb.moovich.domain.usecases.GetMovieByIdUseCase
import com.sb.moovich.domain.usecases.GetWatchMoviesUseCase
import com.sb.moovich.presentation.info.ui.MovieInfoFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    private val deleteMovieFromWatchListUseCase: DeleteMovieFromWatchListUseCase,
    private val getWatchMoviesUseCase: GetWatchMoviesUseCase,
    private val addMovieToRecentUseCase: AddMovieToRecentUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<MovieInfoFragmentState>(MovieInfoFragmentState.Loading)
    val state = _state.asStateFlow()

    private val _bookmarkChecked = MutableStateFlow<Boolean?>(null)
    val bookmarkChecked = _bookmarkChecked.asStateFlow()

    fun getMovieById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieByIdUseCase(movieId)?.let { movieInfo ->
                val filteredMovie =
                    movieInfo.copy(
                        actors = movieInfo.actors.filter { it.name.isNotEmpty() },
                        similarMovies = movieInfo.similarMovies.filter { it.name.isNotEmpty() },
                    )
                _state.value = MovieInfoFragmentState.Content(filteredMovie)
                _bookmarkChecked.value =
                    getWatchMoviesUseCase().firstOrNull { it.id == filteredMovie.id } != null
                addMovieToRecentUseCase(filteredMovie)
            }
        }
    }

    fun addMovieToWatchList(movie: Movie) {
        viewModelScope.launch {
            addMovieToWatchListUseCase(movie)
        }
    }

    fun deleteMovieFromWatchList(movie: Movie) {
        viewModelScope.launch {
            deleteMovieFromWatchListUseCase(movie)
        }
    }

    fun reverseBookmarkValue() {
        _bookmarkChecked.value?.let {
            _bookmarkChecked.value = !it
        }
    }
}
