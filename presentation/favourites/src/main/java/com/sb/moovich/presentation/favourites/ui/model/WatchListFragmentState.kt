package com.sb.moovich.presentation.favourites.ui.model

import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.favourites.adapter.Genre

sealed interface WatchListFragmentState {
    data object Loading : WatchListFragmentState
    data object EmptyContent: WatchListFragmentState

    data class Content(
        val movieList: List<Movie>,
        val genres: List<Genre>
    ) : WatchListFragmentState
}
