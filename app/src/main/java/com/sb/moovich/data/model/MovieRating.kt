package com.sb.moovich.data.model

import com.google.gson.annotations.SerializedName

data class MovieRating(
    @SerializedName("kp")
    val kinopoisk: Double,
)