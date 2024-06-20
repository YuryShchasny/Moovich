package com.sb.moovich.data.network.model

import com.google.gson.annotations.SerializedName

data class ShortMovieDocDto(
    @SerializedName("docs")
    val docs: List<ShortMovieInfoDto>?,
)
