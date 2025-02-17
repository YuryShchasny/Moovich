package com.sb.moovich.presentation.gpt.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.domain.entity.Message
import com.sb.moovich.domain.repository.GPTRepository
import com.sb.moovich.presentation.gpt.ui.model.AssistantFragmentEvent
import com.sb.moovich.presentation.gpt.ui.model.AssistantFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val repository: GPTRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AssistantFragmentState>(AssistantFragmentState.Loading)
    val state = _state.asStateFlow()

    init {
        launch {
            repository.getHistory().collect { history ->
                _state.update {
                    AssistantFragmentState.Content(history, false)
                }
            }
        }
    }

    fun fetchEvent(event: AssistantFragmentEvent) {
        when (event) {
            AssistantFragmentEvent.ClearHistory -> {}
            is AssistantFragmentEvent.SendMessage -> sendMessage(event.message)
        }
    }

    private fun sendMessage(message: String) {
        launch {
            startSending(message)
            repository.sendMessage(message)
        }
    }

    private fun startSending(message: String) {
        _state.update {
            (it as AssistantFragmentState.Content).copy(
                messages = it.messages + Message(Message.Role.USER, message, 0L),
                sending = true
            )
        }
    }
}
