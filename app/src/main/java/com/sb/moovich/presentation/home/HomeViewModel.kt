package com.sb.moovich.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.usecases.GetRecommendedMoviesUseCase
import com.sb.moovich.presentation.search.SearchFragmentState
import com.sb.moovich.presentation.utils.mergeWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeFragmentState>(HomeFragmentState.Loading)
    val state = _state/*.mergeWith(
        getRecommendedMoviesUseCase().map { HomeFragmentState.Content(it)}
    )*/
}