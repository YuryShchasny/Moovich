package com.sb.moovich.presentation.search.ui.model.search

import com.sb.moovich.domain.entity.Movie

sealed interface SearchFragmentEvent {
    data object RecentMovies: SearchFragmentEvent
    data class FindMovie(
        val name: String,
    ): SearchFragmentEvent
    data object ResetFilters: SearchFragmentEvent
    data object OnFilterClick: SearchFragmentEvent
    data class OnMovieClick(val movie: Movie): SearchFragmentEvent
    data object OnSeeAllClick: SearchFragmentEvent
}