package com.sb.moovich.presentation.search.model.search

import com.sb.moovich.domain.entity.Movie

sealed class SearchFragmentState {
    data object Loading : SearchFragmentState()

    data class Error(
        val msg: String,
    ) : SearchFragmentState()

    data object Filters : SearchFragmentState()

    sealed class Content(
        val list: List<Movie>,
        var seeAll: Boolean,
    ) : SearchFragmentState() {
        data class FindList(
            val findList: List<Movie>,
            val searchName: String,
            val seeAllFindList: Boolean = false,
        ) : Content(findList, seeAllFindList)

        data class RecentList(
            val recentList: List<Movie>,
            val seeAllRecentList: Boolean = false,
        ) : Content(recentList, seeAllRecentList)
    }
}
