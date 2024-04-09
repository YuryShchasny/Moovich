package com.sb.moovich.presentation.home

import androidx.lifecycle.ViewModel
import com.sb.moovich.domain.usecases.GetRecommendedMoviesUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase
) : ViewModel() {
    val recommendedMovies = getRecommendedMoviesUseCase()
}