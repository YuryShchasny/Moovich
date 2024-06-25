package com.sb.moovich.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.core.R
import com.sb.moovich.core.extensions.mergeWith
import com.sb.moovich.domain.usecases.GetRecommendedMoviesUseCase
import com.sb.moovich.presentation.home.ui.HomeFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomeFragmentState>(HomeFragmentState.Loading)
    val state = _state.asStateFlow()

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                HomeFragmentState.Content(getRecommendedMoviesUseCase())
            }
        }
    }
}
