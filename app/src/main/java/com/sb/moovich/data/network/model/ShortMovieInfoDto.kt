package com.sb.moovich.data.network.model

import com.google.gson.annotations.SerializedName

data class ShortMovieInfoDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("alternativeName")
    val alternativeName: String?,
    @SerializedName("poster")
    val poster: MoviePoster?,
    @SerializedName("rating")
    val rating: MovieRating?,
)
