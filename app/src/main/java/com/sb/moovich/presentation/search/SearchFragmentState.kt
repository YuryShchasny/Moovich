package com.sb.moovich.presentation.search

import com.sb.moovich.domain.entity.MediumMovieInfo

sealed class SearchFragmentState {
    data object Loading : SearchFragmentState()

    data class Error(val msg: String) : SearchFragmentState()

    data object Filters : SearchFragmentState()
    sealed class Content(val list: List<MediumMovieInfo>, var seeAll: Boolean) : SearchFragmentState() {
        data class FindList(
            val findList: List<MediumMovieInfo>,
            val searchName: String,
            val seeAllFindList: Boolean = false,
        ) : Content(findList, seeAllFindList)

        data class RecentList(
            val recentList: List<MediumMovieInfo>,
            val seeAllRecentList: Boolean = false,
        ) : Content(recentList, seeAllRecentList)
    }

}