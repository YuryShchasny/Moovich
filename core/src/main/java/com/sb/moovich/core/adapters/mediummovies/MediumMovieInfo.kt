package com.sb.moovich.core.adapters.mediummovies

import com.sb.moovich.domain.entity.Movie

sealed interface MediumMovie {
    data class Info(val movie: Movie) : MediumMovie

    data object Shimmer : MediumMovie
}
