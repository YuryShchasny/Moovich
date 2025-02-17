package com.sb.moovich.presentation.info.ui.model

import com.sb.moovich.domain.entity.Movie

sealed interface MovieInfoFragmentState {
    data object Loading : MovieInfoFragmentState
    data class Content(
        val movie: Movie,
        val bookMarkChecked: Boolean = false,
        val seeAllActors: Boolean = false,
    ) : MovieInfoFragmentState
}
