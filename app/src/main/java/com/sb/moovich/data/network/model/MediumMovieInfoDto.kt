package com.sb.moovich.data.network.model

import com.google.gson.annotations.SerializedName

data class MediumMovieInfoDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("alternativeName")
    val alternativeName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("rating")
    val rating: MovieRating?,
    @SerializedName("poster")
    val poster: MoviePoster?,
    @SerializedName("movieLength")
    val movieLength: Int?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("genres")
    val genres: List<Genre>?,
)
