package com.sb.moovich.presentation.info.adapter.actors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.extensions.load
import com.sb.moovich.domain.entity.Actor
import com.sb.moovich.presentation.info.databinding.ItemActorBinding

class ActorItemViewHolder(private val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(actor: Actor) {
        binding.imageViewActorPhoto.load(actor.photo)
        val name = actor.name
        val parts = name.split(" ")
        binding.textViewActorFirstName.text = parts[0]
        if (parts.size > 1) {
            binding.textViewActorLastName.text = parts[1]
        }
        binding.textViewActorDescription.text = actor.description
    }

    companion object {
        fun from(parent: ViewGroup): ActorItemViewHolder {
            return ActorItemViewHolder(
                ItemActorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
