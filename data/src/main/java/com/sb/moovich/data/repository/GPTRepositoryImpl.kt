package com.sb.moovich.data.repository

import com.sb.moovich.data.local.datasource.MessageLocalDataSource
import com.sb.moovich.data.remote.datasource.GPTRemoteDataSource
import com.sb.moovich.domain.entity.Message
import com.sb.moovich.domain.repository.GPTRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

class GPTRepositoryImpl @Inject constructor(
    private val remoteDataSource: GPTRemoteDataSource,
    private val localDataSource: MessageLocalDataSource
) : GPTRepository {

    private val history = MutableStateFlow<List<Message>>(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            history.update { localDataSource.getHistory() }
        }
    }

    override suspend fun sendMessage(message: String) {
        val sendMessage = Message(
            role = Message.Role.USER,
            content = message,
            date = Instant.now().epochSecond
        )
        val resultMessage = remoteDataSource.sendMessage(sendMessage, history.value)
        resultMessage?.let {
            localDataSource.addMessageToHistory(sendMessage)
            localDataSource.addMessageToHistory(resultMessage)
            history.update { it + sendMessage + resultMessage }
        }
    }

    override suspend fun getHistory(): StateFlow<List<Message>> = history.asStateFlow()

    override suspend fun clearHistory() {
        localDataSource.clearHistory()
        history.update { emptyList() }
    }
}
