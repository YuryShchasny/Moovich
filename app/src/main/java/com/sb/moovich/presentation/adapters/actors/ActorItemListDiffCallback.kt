package com.sb.moovich.presentation.adapters.actors

import androidx.recyclerview.widget.DiffUtil
import com.sb.moovich.domain.entity.Actor

class ActorItemListDiffCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}