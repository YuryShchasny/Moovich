package com.sb.moovich.presentation.all.ui

import com.sb.moovich.domain.entity.Movie

sealed interface AllMoviesState {
    data object Loading: AllMoviesState
    data class Movies(val movieList: List<Movie>): AllMoviesState
}
