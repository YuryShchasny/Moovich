package com.sb.moovich.core.adapters.shortmovies

sealed interface ShortMovie {
    data class ShortMovieInfo(
        val id: Int,
        val name: String,
        val rating: Double,
        val poster: String
    ): ShortMovie
    data object Shimmer: ShortMovie
}