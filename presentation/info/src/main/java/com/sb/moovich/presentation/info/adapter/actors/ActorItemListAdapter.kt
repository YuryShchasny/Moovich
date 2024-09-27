package com.sb.moovich.presentation.info.adapter.actors

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sb.moovich.domain.entity.Actor

class ActorItemListAdapter : ListAdapter<Actor, ActorItemViewHolder>(ActorItemListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorItemViewHolder =
        ActorItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ActorItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
