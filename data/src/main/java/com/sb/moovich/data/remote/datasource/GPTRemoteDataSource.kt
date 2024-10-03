package com.sb.moovich.data.remote.datasource

import com.sb.moovich.data.mapper.MessageDtoMapper
import com.sb.moovich.data.remote.api.GPTApi
import com.sb.moovich.data.remote.dto.MessageDto
import com.sb.moovich.data.remote.dto.SendMessageRequestDto
import com.sb.moovich.domain.entity.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GPTRemoteDataSource @Inject constructor(
    private val api: GPTApi,
    private val mapper: MessageDtoMapper
) {
    suspend fun sendMessage(message: Message, history: List<Message>): Message? {
        val systemMessage = MessageDto(SYSTEM_ROLE, SYSTEM_MESSAGE)
        val userMessage = mapper.mapEntityToData(message)
        val messages =
            listOf(systemMessage) + history.takeLast(10).map { mapper.mapEntityToData(it) } + userMessage
        val response = api.sendMessage(SendMessageRequestDto(GPT_MODEL, messages))
        val responseMessage = response.body()?.choices?.first()?.message
        return responseMessage?.let {
            mapper.mapDataToEntity(it)
        }
    }

    companion object {
        private const val GPT_MODEL = "gpt-3.5-turbo-0125"
        private const val SYSTEM_ROLE = "system"
        private const val SYSTEM_MESSAGE =
            "You select films, TV series and cartoons according to user preferences."
    }
}
