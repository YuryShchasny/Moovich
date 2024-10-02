package com.sb.moovich.domain.entity

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class Message(
    val role: Role,
    val content: String,
    val date: Long?,
) {
    enum class Role {
        GPT,
        USER
    }

    fun convertDate(): String? {
        return date?.let {
            return if(it == 0L)  ""
            else Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"))
        }
    }
}
