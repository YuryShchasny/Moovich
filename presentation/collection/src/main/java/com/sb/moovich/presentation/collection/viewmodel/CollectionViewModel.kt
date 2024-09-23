package com.sb.moovich.presentation.collection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.usecases.all.GetAllMoviesUseCase
import com.sb.moovich.domain.usecases.all.MovieNextPageUseCase
import com.sb.moovich.presentation.collection.ui.CollectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val movieNextPageUseCase: MovieNextPageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CollectionState>(CollectionState.Loading)
    val state = _state.asStateFlow()

    fun init(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMoviesUseCase(GetAllType.Collection(slug)).collect { list ->
                _state.update {
                    if (it is CollectionState.Movies) it.copy(movieList = it.movieList + list)
                    else CollectionState.Movies(list)
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