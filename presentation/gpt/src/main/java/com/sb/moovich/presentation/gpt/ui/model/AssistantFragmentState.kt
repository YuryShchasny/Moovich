package com.sb.moovich.presentation.gpt.ui.model

import com.sb.moovich.domain.entity.Message

sealed interface AssistantFragmentState {
    data object Loading : AssistantFragmentState
    data class Content(val messages: List<Message>, val sending: Boolean): AssistantFragmentState
}
