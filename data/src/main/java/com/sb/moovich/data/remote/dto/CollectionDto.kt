package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CollectionDto(
    @SerializedName("slug") val slug: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("moviesCount") val count: Int?,
    @SerializedName("cover") val cover: CoverDto?
)
