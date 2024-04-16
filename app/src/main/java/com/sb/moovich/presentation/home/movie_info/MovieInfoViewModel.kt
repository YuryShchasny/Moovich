package com.sb.moovich.presentation.home.movie_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import com.sb.moovich.domain.usecases.AddMovieToWatchListUseCase
import com.sb.moovich.domain.usecases.DeleteMovieFromWatchListUseCase
import com.sb.moovich.domain.usecases.GetMovieByIdFromDatabaseUseCase
import com.sb.moovich.domain.usecases.GetMovieByIdUseCase
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
    private val getMovieByIdFromDatabaseUseCase: GetMovieByIdFromDatabaseUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<MovieInfoFragmentState>(MovieInfoFragmentState.Loading)
    val state = _state.asStateFlow()

    private val _bookmarkChecked = MutableStateFlow<Boolean?>(null)
    val bookmarkChecked = _bookmarkChecked.asStateFlow()

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                getMovieByIdUseCase(movieId).collect {movieInfo ->
                    _state.value = MovieInfoFragmentState.Content(movieInfo)
                    getMovieByIdFromDatabaseUseCase(movieInfo.id).collect {shortMovieInfo ->
                        _bookmarkChecked.value = shortMovieInfo!=null
                    }
                }
            }
        }
    }

    fun addMovieToWatchList(movie: MovieInfo) {
        viewModelScope.launch {
            addMovieToWatchListUseCase(ShortMovieInfo(movie.id, movie.name, movie.rating, movie.poster))
        }
    }
    fun deleteMovieFromWatchList(movie: MovieInfo) {
        viewModelScope.launch {
            deleteMovieFromWatchListUseCase(ShortMovieInfo(movie.id, movie.name, movie.rating, movie.poster))
        }
    }

    fun reverseBookmarkValue() {
        _bookmarkChecked.value?.let {
            _bookmarkChecked.value = !it
        }
    }
}