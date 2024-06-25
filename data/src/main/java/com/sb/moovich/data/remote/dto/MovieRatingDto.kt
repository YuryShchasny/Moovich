package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieRatingDto(
    @SerializedName("kp")
    val kinopoisk: Double?,
    @SerializedName("imdb")
    val imdb: Double? = 0.0,
)
