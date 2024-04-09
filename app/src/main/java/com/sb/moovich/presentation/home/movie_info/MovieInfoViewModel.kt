package com.sb.moovich.presentation.home.movie_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.usecases.GetMovieByIdUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieInfoViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MovieInfoFragmentState>(MovieInfoFragmentState.Loading)
    val state = _state.asStateFlow()

    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                getMovieByIdUseCase(movieId).collect {
                    _state.value = MovieInfoFragmentState.Content(it)
                }
            }
        }
    }

}