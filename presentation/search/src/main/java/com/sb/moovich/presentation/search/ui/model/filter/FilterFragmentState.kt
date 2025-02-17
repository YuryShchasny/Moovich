package com.sb.moovich.presentation.search.ui.model.filter

import com.sb.moovich.domain.entity.Filter

sealed interface FilterFragmentState {

    data class Content(
        val filter: Filter,
        val genres: List<String>,
        val countries: List<String>
    ) : FilterFragmentState

    data object Loading : FilterFragmentState
}
