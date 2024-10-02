package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.Message
import kotlinx.coroutines.flow.StateFlow

interface GPTRepository {
    suspend fun sendMessage(message: String)

    suspend fun getHistory(): StateFlow<List<Message>>

    suspend fun clearHistory()
}
