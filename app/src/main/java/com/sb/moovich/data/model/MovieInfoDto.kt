package com.sb.moovich.data.model

import com.google.gson.annotations.SerializedName

data class MovieInfoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("rating")
    val rating: MovieRating,
    @SerializedName("poster")
    val poster: MoviePoster,
    @SerializedName("backdrop")
    val backdrop: Backdrop,
    @SerializedName("movieLength")
    val movieLength: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("persons")
    val persons: List<Person>,
    @SerializedName("similarMovies")
    val similarMovies: List<ShortInfoMovieDto>?

)