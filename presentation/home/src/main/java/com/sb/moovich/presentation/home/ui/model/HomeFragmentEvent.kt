package com.sb.moovich.presentation.home.ui.model

import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.Movie

sealed interface HomeFragmentEvent {
    data class OnMovieClick(val movie: Movie): HomeFragmentEvent
    data class OnCollectionClick(val collection: Collection): HomeFragmentEvent
    data class OnGenreClick(val genre: String): HomeFragmentEvent
    data object SeeAllRecommendations: HomeFragmentEvent
    data object SeeAllCollections: HomeFragmentEvent
    data object SeeAllSeries: HomeFragmentEvent
}