package com.sb.moovich.presentation.favourites.ui

import com.sb.moovich.domain.entity.Movie

sealed class WatchListFragmentState {
    data object Loading : WatchListFragmentState()

    data class Error(
        val msgResId: Int,
    ) : WatchListFragmentState()

    data class Content(
        val movieList: List<Movie>,
    ) : WatchListFragmentState()
}
