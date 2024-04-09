package com.sb.moovich.presentation.adapters.actors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sb.moovich.R
import com.sb.moovich.domain.entity.Actor
import com.sb.moovich.domain.entity.ShortMovieInfo
import com.sb.moovich.presentation.adapters.movies.MovieItemListDiffCallback
import com.sb.moovich.presentation.adapters.movies.MovieItemViewHolder

class ActorItemListAdapter(private val context: Context) : ListAdapter<Actor, ActorItemViewHolder>(
    ActorItemListDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorItemViewHolder {
        val inflater = LayoutInflater.from(context)
        return ActorItemViewHolder(inflater.inflate(R.layout.item_actor, parent, false))
    }


    override fun onBindViewHolder(holder: ActorItemViewHolder, position: Int) {
        val currentActor = currentList[position]
        holder.imageViewActorPhoto.load(currentActor.photo)
        val name = currentActor.name
        val parts = name.split(" ")
        holder.textViewActorFirstName.text = parts[0]
        holder.textViewActorLastName.text = parts[1]
        holder.textViewActorDescription.text = currentActor.description
    }

}