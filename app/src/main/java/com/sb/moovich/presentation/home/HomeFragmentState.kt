package com.sb.moovich.presentation.home

import com.sb.moovich.domain.entity.ShortMovieInfo

sealed class HomeFragmentState {
    data object Loading : HomeFragmentState()

    data class Error(val msg: String) : HomeFragmentState()

    data class Content(
        val recommendedList: List<ShortMovieInfo>,
    ) : HomeFragmentState()
}