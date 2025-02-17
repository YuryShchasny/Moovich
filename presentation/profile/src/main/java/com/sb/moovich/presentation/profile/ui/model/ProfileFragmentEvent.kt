package com.sb.moovich.presentation.profile.ui.model

sealed interface ProfileFragmentEvent {
    data object Logout: ProfileFragmentEvent
    data object ClearAssistantHistory: ProfileFragmentEvent
    data object ClearCache: ProfileFragmentEvent
}