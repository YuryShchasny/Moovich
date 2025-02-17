package com.sb.moovich.presentation.collection.ui.model

import com.sb.moovich.domain.entity.Movie

sealed interface CollectionFragmentEvent {
    data class OnMovieClick(val movie: Movie): CollectionFragmentEvent
}
