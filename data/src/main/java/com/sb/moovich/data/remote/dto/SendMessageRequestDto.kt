package com.sb.moovich.data.remote.dto

data class SendMessageRequestDto(
    val model: String,
    val messages: List<MessageDto>
)
