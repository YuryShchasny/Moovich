package com.sb.moovich.presentation.search.model.search

sealed class SearchFragmentEvent {
    data object RecentMovies: SearchFragmentEvent()
    data class FindMovie(
        val name: String,
        val count: Int,
    ): SearchFragmentEvent()
    data object ResetFilters: SearchFragmentEvent()
}