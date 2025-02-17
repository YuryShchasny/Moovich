package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.ChatCompletionDto
import com.sb.moovich.data.remote.dto.SendMessageRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GPTApi {

    @POST("chat/completions")
    suspend fun sendMessage(
        @Body requestBody: SendMessageRequestDto,
    ): Response<ChatCompletionDto>

    companion object {

    }
}