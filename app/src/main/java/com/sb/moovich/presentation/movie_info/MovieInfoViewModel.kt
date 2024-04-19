package com.sb.moovich.presentation.movie_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.MediumMovieInfo
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.usecases.AddMovieToRecentUseCase
import com.sb.moovich.domain.usecases.AddMovieToWatchListUseCase
import com.sb.moovich.domain.usecases.DeleteMovieFromWatchListUseCase
import com.sb.moovich.domain.usecases.GetMovieByIdUseCase
import com.sb.moovich.domain.usecases.GetWatchMovieByIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieInfoViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val addMovieToWatchListUseCase: AddMovieToWatchListUseCase,
    private val deleteMovieFromWatchListUseCase: DeleteMovieFromWatchListUseCase,
    private val getWatchMovieByIdUseCase: GetWatchMovieByIdUseCase,
    private val addMovieToRecentUseCase: AddMovieToRecentUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<MovieInfoFragmentState>(MovieInfoFragmentState.Loading)
    val state = _state.asStateFlow()

    private val _bookmarkChecked = MutableStateFlow<Boolean?>(null)
    val bookmarkChecked = _bookmarkChecked.asStateFlow()

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                getMovieByIdUseCase(movieId).collect { movieInfo ->
                    val filteredMovie =
                        movieInfo.copy (
                            actors = movieInfo.actors.filter { it.name.isNotEmpty() },
                            similarMovies = movieInfo.similarMovies.filter { it.name.isNotEmpty() }
                        )
                    _state.value = MovieInfoFragmentState.Content(filteredMovie)
                    addMovieToRecentUseCase(parseToMediumMovie(filteredMovie))
                    getWatchMovieByIdUseCase(filteredMovie.id).collect { watchMovie ->
                        _bookmarkChecked.value = watchMovie != null
                    }
                }
            }
        }
    }

    private fun parseToMediumMovie(movie: MovieInfo) =
        MediumMovieInfo(
            movie.id,
            movie.name,
            movie.description,
            movie.rating,
            movie.poster,
            movie.movieLength,
            movie.year,
            movie.genres
        )

    fun addMovieToWatchList(movie: MovieInfo) {
        viewModelScope.launch {
            addMovieToWatchListUseCase(
                parseToMediumMovie(movie)
            )
        }
    }

    fun deleteMovieFromWatchList(movie: MovieInfo) {
        viewModelScope.launch {
            deleteMovieFromWatchListUseCase(
                parseToMediumMovie(movie)
            )
        }
    }

    fun reverseBookmarkValue() {
        _bookmarkChecked.value?.let {
            _bookmarkChecked.value = !it
        }
    }
}