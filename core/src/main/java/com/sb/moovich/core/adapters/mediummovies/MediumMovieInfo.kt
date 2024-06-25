package com.sb.moovich.core.adapters.mediummovies

data class MediumMovieInfo(
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double,
    val poster: String,
    val year: Int,
    val movieLength: Int,
    val genres: List<String>
)
