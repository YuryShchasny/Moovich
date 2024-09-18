package com.sb.moovich.presentation.search.model.search

import com.sb.moovich.domain.entity.Movie

sealed class SearchFragmentState {
    data object Loading : SearchFragmentState()

    data class Error(
        val msg: String,
    ) : SearchFragmentState()

    sealed class Content(
        var seeAll: Boolean,
    ) : SearchFragmentState() {
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
            val seeAllFilterList: Boolean
        ) : Content(seeAllFilterList)
    }
}
