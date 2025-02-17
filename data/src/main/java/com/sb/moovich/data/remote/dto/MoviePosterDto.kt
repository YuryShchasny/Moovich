package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviePosterDto(
    @SerializedName("url")
    val url: String?,
    @SerializedName("previewUrl")
    val previewUrl: String?,
)
