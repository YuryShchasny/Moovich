package com.sb.moovich.presentation.home.ui

import com.sb.moovich.domain.entity.Movie

sealed class HomeFragmentState {
    data object Loading : HomeFragmentState()

    data class Error(
        val msg: String,
    ) : HomeFragmentState()

    data class Content(
        val recommendedList: List<Movie>,
    ) : HomeFragmentState()
}
