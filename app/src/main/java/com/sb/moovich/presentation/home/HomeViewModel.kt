package com.sb.moovich.presentation.home

import androidx.lifecycle.ViewModel
import com.sb.moovich.domain.usecases.GetRecommendedMoviesUseCase
import com.sb.moovich.presentation.extensions.mergeWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow<HomeFragmentState>(HomeFragmentState.Loading)
        val state =
            _state.mergeWith(
                getRecommendedMoviesUseCase().map { HomeFragmentState.Content(it) },
            )
    }
