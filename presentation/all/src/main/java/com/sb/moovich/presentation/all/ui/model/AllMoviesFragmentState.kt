package com.sb.moovich.presentation.all.ui.model

import com.sb.moovich.domain.entity.Movie

sealed interface AllMoviesFragmentState {
    data object Loading: AllMoviesFragmentState
    data class Movies(val movieList: List<Movie>, val lastPage: Boolean = false): AllMoviesFragmentState
}
