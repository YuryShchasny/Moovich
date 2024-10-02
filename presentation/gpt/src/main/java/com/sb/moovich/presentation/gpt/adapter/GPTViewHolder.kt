package com.sb.moovich.presentation.gpt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.gpt.databinding.ItemGptMessageBinding

class GPTViewHolder(
    private val binding: ItemGptMessageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: MessageUI.GPT) {
        binding.messageTextView.text = message.message
        binding.dateTextView.text = message.date
        binding.dateTextView.visibility = if (message.date.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    companion object {
        fun from(parent: ViewGroup): GPTViewHolder {
            return GPTViewHolder(
                ItemGptMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
