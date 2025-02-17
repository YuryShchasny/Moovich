package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CollectionDocDto(
    @SerializedName("docs")
    val docs: List<CollectionDto>?,
)
