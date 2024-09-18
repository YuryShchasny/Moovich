package com.sb.moovich.presentation.search.model.filter

import com.sb.moovich.domain.entity.Filter

sealed class FilterFragmentEvent {
    data class SaveFilter(val filter: Filter) : FilterFragmentEvent()
    data class UpdateFilter(val filter: Filter) : FilterFragmentEvent()
    data class UpdateSliders(
        val yearFrom: Int,
        val yearTo: Int,
        val ratingFrom: Int,
        val ratingTo: Int
    ) : FilterFragmentEvent()

    data object Reset : FilterFragmentEvent()
}