package com.sb.moovich.data.mapper

import com.sb.moovich.data.local.dbo.MessageDbo
import com.sb.moovich.domain.entity.Message
import javax.inject.Inject

class MessageDboMapper @Inject constructor(): IDataMapper<MessageDbo, Message> {
    override suspend fun mapDataToEntity(data: MessageDbo): Message {
        return Message(
            role = data.role,
            content = data.content,
            date = data.date
        )
    }

    override suspend fun mapEntityToData(entity: Message): MessageDbo {
        return MessageDbo(
            role = entity.role,
            content = entity.content,
            date = entity.date
        )
    }
}
