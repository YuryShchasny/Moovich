package com.sb.moovich.presentation.search.model.filter

import com.sb.moovich.domain.entity.Filter

sealed class FilterFragmentState {
    data class Content(
        val filter: Filter,
        val genres: List<String>,
        val countries: List<String>
    ) : FilterFragmentState()

    data class FilterState(val filter: Filter): FilterFragmentState()

    data object Loading : FilterFragmentState()
}