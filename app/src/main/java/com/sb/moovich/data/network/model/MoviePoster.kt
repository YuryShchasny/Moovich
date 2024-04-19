package com.sb.moovich.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviePoster (
    @SerializedName("previewUrl")
    val previewUrl: String?
)