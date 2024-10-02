package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("role") val role: String?,
    @SerializedName("content") val content: String?,
)
