package com.sb.moovich.presentation.gpt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.gpt.databinding.ItemUserMessageBinding

class UserViewHolder(
    private val binding: ItemUserMessageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: MessageUI.User) {
        binding.messageTextView.text = message.message
        message.date?.let {
            binding.dateTextView.text = if (it.isEmpty()) ContextCompat.getString(
                binding.root.context,
                com.sb.moovich.core.R.string.sending
            ) else message.date
        }
        binding.dateTextView.visibility = if (message.date == null) View.GONE else View.VISIBLE
    }

    companion object {
        fun from(parent: ViewGroup): UserViewHolder {
            return UserViewHolder(
                ItemUserMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
