package com.sb.moovich.presentation.watch_list

import com.sb.moovich.domain.entity.MediumMovieInfo

sealed class WatchListFragmentState {
    data object Loading : WatchListFragmentState()

    data class Error(val msgResId: Int) : WatchListFragmentState()

    data class Content(
        val movieList: List<MediumMovieInfo>
    ) : WatchListFragmentState()
}