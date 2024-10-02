package com.sb.moovich.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sb.moovich.domain.entity.Message

@Entity("messages")
data class MessageDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val role: Message.Role,
    val content: String,
    val date: Long?,
)
