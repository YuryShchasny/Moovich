package com.sb.moovich.presentation.gpt.adapter

import androidx.recyclerview.widget.DiffUtil

class MessagesDiffCallback : DiffUtil.ItemCallback<MessageUI>() {
    override fun areItemsTheSame(oldItem: MessageUI, newItem: MessageUI): Boolean {
        return if (oldItem is MessageUI.User && newItem is MessageUI.User) oldItem.message == newItem.message
        else if (oldItem is MessageUI.GPT && newItem is MessageUI.GPT) oldItem.message == newItem.message
        else oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: MessageUI, newItem: MessageUI): Boolean {
        return if (oldItem is MessageUI.User && newItem is MessageUI.User) oldItem == newItem
        else if (oldItem is MessageUI.GPT && newItem is MessageUI.GPT) oldItem == newItem
        else oldItem == newItem
    }
}
