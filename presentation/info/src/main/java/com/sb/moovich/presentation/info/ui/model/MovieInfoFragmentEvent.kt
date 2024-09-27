package com.sb.moovich.presentation.info.ui.model

import com.sb.moovich.domain.entity.Movie

sealed interface MovieInfoFragmentEvent {
    data object OnBookmarkClick: MovieInfoFragmentEvent
    data object SeeAllActors: MovieInfoFragmentEvent
    data class OnSimilarMovieClick(val movie: Movie): MovieInfoFragmentEvent
}