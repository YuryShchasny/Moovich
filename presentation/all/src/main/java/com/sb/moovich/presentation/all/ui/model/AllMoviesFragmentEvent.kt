package com.sb.moovich.presentation.all.ui.model

import com.sb.moovich.domain.entity.Movie

interface AllMoviesFragmentEvent {
    data class OnMovieClick(val movie: Movie): AllMoviesFragmentEvent
}