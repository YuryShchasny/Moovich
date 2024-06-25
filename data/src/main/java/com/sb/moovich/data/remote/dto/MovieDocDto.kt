package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDocDto(
    @SerializedName("docs")
    val docs: List<MovieDto>?,
)