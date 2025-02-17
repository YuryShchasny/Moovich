package com.sb.moovich.presentation.favourites.ui.model

import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.favourites.adapter.Genre

sealed interface WatchListFragmentEvent {
    data class OnGenreClick(val genre: Genre): WatchListFragmentEvent
    data class OnMovieClick(val movie: Movie): WatchListFragmentEvent
}