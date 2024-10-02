package com.sb.moovich.presentation.gpt.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.domain.entity.Message

class MessagesAdapter :
    ListAdapter<MessageUI, RecyclerView.ViewHolder>(MessagesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            USER_TYPE -> UserViewHolder.from(parent)
            GPT_TYPE -> GPTViewHolder.from(parent)
            SHIMMER_TYPE -> ShimmerViewHolder.from(parent)
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val message = currentList[position]) {
            is MessageUI.User -> (holder as UserViewHolder).bind(message)
            is MessageUI.GPT -> (holder as GPTViewHolder).bind(message)
            MessageUI.Shimmer -> (holder as ShimmerViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is MessageUI.User -> USER_TYPE
            is MessageUI.GPT -> GPT_TYPE
            MessageUI.Shimmer -> SHIMMER_TYPE
        }
    }

    fun submit(list: List<Message>) {
        val mappedList = list.map {
            when (it.role) {
                Message.Role.GPT -> MessageUI.GPT(it.content, it.convertDate())
                Message.Role.USER -> MessageUI.User(it.content, it.convertDate())
            }
        }
        submitList(mappedList)
    }

    fun submitShimmerList(count: Int) {
        submitList((0..<count).map { MessageUI.Shimmer })
    }

    fun submit(list: List<Message>, commitCallback: Runnable?) {
        val mappedList = list.map {
            when (it.role) {
                Message.Role.GPT -> MessageUI.GPT(it.content, it.convertDate())
                Message.Role.USER -> MessageUI.User(it.content, it.convertDate())
            }
        }
        submitList(mappedList, commitCallback)
    }

    companion object {
        private const val USER_TYPE = 0
        private const val GPT_TYPE = 1
        private const val SHIMMER_TYPE = 2
    }
}