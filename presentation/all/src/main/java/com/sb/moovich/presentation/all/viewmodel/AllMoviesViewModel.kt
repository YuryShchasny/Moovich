package com.sb.moovich.presentation.all.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.usecases.all.GetAllMoviesUseCase
import com.sb.moovich.domain.usecases.all.MovieNextPageUseCase
import com.sb.moovich.presentation.all.ui.AllMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val movieNextPageUseCase: MovieNextPageUseCase
): ViewModel() {
    private val _state = MutableStateFlow<AllMoviesState>(AllMoviesState.Loading)
    val state = _state.asStateFlow()

    fun initMovies(type: GetAllType) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMoviesUseCase(type).collect { list ->
                _state.update {
                    if(it is AllMoviesState.Movies) it.copy(movieList = it.movieList + list)
                    else AllMoviesState.Movies(list)
                }
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            movieNextPageUseCase()
        }
    }
}