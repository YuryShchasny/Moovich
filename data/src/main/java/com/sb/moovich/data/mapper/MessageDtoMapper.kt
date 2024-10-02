package com.sb.moovich.data.mapper

import com.sb.moovich.data.remote.dto.MessageDto
import com.sb.moovich.domain.entity.Message
import java.time.Instant
import javax.inject.Inject

class MessageDtoMapper @Inject constructor() : IDataMapper<MessageDto, Message> {
    override suspend fun mapDataToEntity(data: MessageDto): Message {
        return Message(
            role = if (data.role == "user") Message.Role.USER else Message.Role.GPT,
            content = data.content ?: "",
            date = Instant.now().epochSecond
        )
    }

    override suspend fun mapEntityToData(entity: Message): MessageDto {
        return MessageDto(
            role = if (entity.role == Message.Role.USER) "user" else "assistant",
            content = entity.content
        )
    }
}
