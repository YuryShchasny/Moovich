package com.sb.moovich.domain.entity

data class MovieInfo(
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double,
    val poster: String,
    val backdrop: String,
    val movieLength: Int,
    val urlWatch: String,
    val year: Int,
    val genres: List<String?>,
    val actors: List<Actor>,
    val similarMovies: List<ShortMovieInfo>,
)
