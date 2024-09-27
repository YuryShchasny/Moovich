package com.sb.moovich.presentation.search.ui.model.filter

import com.sb.moovich.domain.entity.MovieType
import com.sb.moovich.domain.entity.SortType

sealed interface FilterFragmentEvent {
    data object SaveFilter : FilterFragmentEvent
    data class OnGenreClick(val genre: String) : FilterFragmentEvent
    data class OnTabClick(val type: MovieType) : FilterFragmentEvent
    data class OnCountryClick(val country: String) : FilterFragmentEvent
    data class OnSortViewClick(val sortType: SortType) : FilterFragmentEvent
    data class UpdateSliders(
        val yearFrom: Int,
        val yearTo: Int,
        val ratingFrom: Int,
        val ratingTo: Int
    ) : FilterFragmentEvent

    data object Reset : FilterFragmentEvent
    data object OnBackPressed : FilterFragmentEvent
}