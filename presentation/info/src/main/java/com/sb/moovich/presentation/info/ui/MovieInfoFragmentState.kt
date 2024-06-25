package com.sb.moovich.presentation.info.ui

import com.sb.moovich.domain.entity.Movie

sealed class MovieInfoFragmentState {
    data object Loading : MovieInfoFragmentState()

    data class Error(
        val msg: String,
    ) : MovieInfoFragmentState()

    data class Content(
        val currencyMovie: Movie,
        var seeAllActors: Boolean = false,
    ) : MovieInfoFragmentState()
}
