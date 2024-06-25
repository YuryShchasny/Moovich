package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("alternativeName")
    val alternativeName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("rating")
    val rating: MovieRatingDto?,
    @SerializedName("poster")
    val poster: MoviePosterDto?,
    @SerializedName("backdrop")
    val backdrop: BackdropDto?,
    @SerializedName("movieLength")
    val movieLength: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("genres")
    val genres: List<GenreDto>?,
    @SerializedName("persons")
    val persons: List<PersonDto>?,
    @SerializedName("similarMovies")
    val similarMovies: List<MovieDto>?,
)
