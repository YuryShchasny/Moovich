package com.sb.moovich.data.model

import com.google.gson.annotations.SerializedName

data class ShortInfoMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster")
    val poster: MoviePoster,
    @SerializedName("rating")
    val rating: MovieRating?
)