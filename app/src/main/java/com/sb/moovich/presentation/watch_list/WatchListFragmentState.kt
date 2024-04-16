package com.sb.moovich.presentation.watch_list

import com.sb.moovich.domain.entity.ShortMovieInfo

sealed class WatchListFragmentState {
    data object Loading : WatchListFragmentState()

    data class Error(val msgResId: Int) : WatchListFragmentState()

    data class Content(
        val movieList: List<ShortMovieInfo>
    ) : WatchListFragmentState()
}