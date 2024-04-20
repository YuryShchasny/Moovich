package com.sb.moovich.data.network.model

import com.google.gson.annotations.SerializedName

data class MediumMovieDocDto (
    @SerializedName("docs")
    val docs: List<MediumMovieInfoDto>?,
)