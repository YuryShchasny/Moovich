package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviePosterDto(
    @SerializedName("previewUrl")
    val previewUrl: String?,
)
