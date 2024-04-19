package com.sb.moovich.presentation.search

import com.sb.moovich.domain.entity.MediumMovieInfo

sealed class SearchFragmentState {
    data object Loading : SearchFragmentState()

    data class Error(val msg: String) : SearchFragmentState()
    sealed class Content(val list: List<MediumMovieInfo>) : SearchFragmentState() {
        data class FindList(
            val findList: List<MediumMovieInfo>,
            val searchName: String
        ) : Content(findList)

        data class RecentList(
            val recentList: List<MediumMovieInfo>
        ) : Content(recentList)
    }

}