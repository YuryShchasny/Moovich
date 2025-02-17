package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChatCompletionDto(
    @SerializedName("choices") val choices: List<ChoiceDto>?,
)
