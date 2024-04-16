package com.sb.moovich.data.model

import com.google.gson.annotations.SerializedName


data class MovieDocDto(
    @SerializedName("docs")
    val docs: List<ShortMovieInfoDto>?,
)