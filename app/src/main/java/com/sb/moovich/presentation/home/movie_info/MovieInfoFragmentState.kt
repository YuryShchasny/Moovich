package com.sb.moovich.presentation.home.movie_info

import com.sb.moovich.domain.entity.MovieInfo

sealed class MovieInfoFragmentState {
    data object Loading : MovieInfoFragmentState()

    data class Error(val msg: String) : MovieInfoFragmentState()

    data class Content(
        val currencyMovie: MovieInfo,
        var seeAllActors: Boolean = false
    ) : MovieInfoFragmentState()
}