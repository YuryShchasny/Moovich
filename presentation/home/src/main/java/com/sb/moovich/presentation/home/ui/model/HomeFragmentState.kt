package com.sb.moovich.presentation.home.ui.model

import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.Movie

sealed interface HomeFragmentState {
    data object Loading : HomeFragmentState
    data class Content(
        val mainBoardList: List<Movie>,
        val top10MonthList: List<Movie>,
        val recommendedList: List<Movie>,
        val collections: List<Collection>,
        val top10Series: List<Movie>,
        val genres: List<String>,
    ) : HomeFragmentState
}
