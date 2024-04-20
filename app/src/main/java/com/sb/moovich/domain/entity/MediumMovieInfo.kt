package com.sb.moovich.domain.entity

data class MediumMovieInfo (
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double,
    val poster: String,
    val movieLength: Int,
    val year: Int,
    val genres: List<String?>,
)