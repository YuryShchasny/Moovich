package com.sb.moovich.presentation.gpt.adapter

sealed interface MessageUI {
    data class User(
        val message: String,
        val date: String?,
    ): MessageUI

    data class GPT(
        val message: String,
        val date: String?
    ): MessageUI

    data object Shimmer: MessageUI
}
