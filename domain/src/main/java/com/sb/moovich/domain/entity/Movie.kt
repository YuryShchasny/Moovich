package com.sb.moovich.domain.entity

data class Movie(
    val id: Int,
    val name: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val poster: String = "",
    val backdrop: String = "",
    val movieLength: Int = 0,
    val urlWatch: String = "",
    val year: Int = 0,
    val genres: List<String> = emptyList(),
    val actors: List<Actor> = emptyList(),
    val similarMovies: List<Movie> = emptyList(),
)
