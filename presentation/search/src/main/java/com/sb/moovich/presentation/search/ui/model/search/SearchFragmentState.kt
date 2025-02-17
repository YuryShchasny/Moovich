package com.sb.moovich.presentation.search.ui.model.search

import com.sb.moovich.domain.entity.Movie

sealed interface SearchFragmentState {
    data object Loading : SearchFragmentState

    sealed class Content(
        val seeAll: Boolean,
    ) : SearchFragmentState {
        data class FindList(
            val findList: List<Movie>,
            val searchName: String,
            val seeAllFindList: Boolean = false,
        ) : Content(seeAllFindList)

        data class RecentList(
            val recentList: List<Movie>,
            val seeAllRecentList: Boolean = false,
        ) : Content(seeAllRecentList)

        data class FilterList(
            val findList: List<Movie>,
            val filtersCount: Int,
            val lastPage: Boolean = false,
        ) : Content(false)
    }
}
