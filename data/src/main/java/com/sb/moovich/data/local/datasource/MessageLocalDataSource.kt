package com.sb.moovich.data.local.datasource

import com.sb.moovich.data.local.dao.MessageDao
import com.sb.moovich.data.mapper.MessageDboMapper
import com.sb.moovich.domain.entity.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageLocalDataSource @Inject constructor(
    private val dao: MessageDao,
    private val mapper: MessageDboMapper
) {
    suspend fun addMessageToHistory(message: Message) {
        dao.insertMessage(mapper.mapEntityToData(message))
    }

    suspend fun getHistory(): List<Message> {
        return dao.getMessages().map { mapper.mapDataToEntity(it) }
    }

    suspend fun clearHistory() = dao.clearAll()
}
