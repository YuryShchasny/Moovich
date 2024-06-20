package com.sb.moovich.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieRating(
    @SerializedName("kp")
    val kinopoisk: Double?,
    @SerializedName("imdb")
    val imdb: Double? = 0.0,
)
