package com.sb.moovich.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.usecases.GetRecommendedMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase
) : ViewModel() {
    val recommendedMovies = getRecommendedMoviesUseCase()
}