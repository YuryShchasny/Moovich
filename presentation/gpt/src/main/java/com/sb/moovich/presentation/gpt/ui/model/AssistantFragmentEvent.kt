package com.sb.moovich.presentation.gpt.ui.model

sealed interface AssistantFragmentEvent {
    data class SendMessage(val message: String): AssistantFragmentEvent
    data object ClearHistory: AssistantFragmentEvent
}
