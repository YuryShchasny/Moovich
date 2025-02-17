package com.sb.moovich.presentation.collection.ui.model

import com.sb.moovich.domain.entity.Movie

sealed interface CollectionFragmentState {
    data object Loading : CollectionFragmentState
    data class Movies(val movieList: List<Movie>, val lastPage: Boolean = false) : CollectionFragmentState
}
