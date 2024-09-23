package com.sb.moovich.presentation.collection.ui

import com.sb.moovich.domain.entity.Movie

sealed interface CollectionState {
    data object Loading : CollectionState
    data class Movies(val movieList: List<Movie>) : CollectionState

}