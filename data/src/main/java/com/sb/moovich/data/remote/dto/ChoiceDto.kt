package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChoiceDto(
    @SerializedName("message") val message: MessageDto?,
)
