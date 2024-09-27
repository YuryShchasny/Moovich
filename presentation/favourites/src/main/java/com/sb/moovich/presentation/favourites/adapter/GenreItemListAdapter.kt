package com.sb.moovich.presentation.favourites.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class GenreItemListAdapter : ListAdapter<Genre, GenreItemViewHolder>(GenreItemListDiffCallback()) {
    var onGenreItemClickListener: ((Genre) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GenreItemViewHolder = GenreItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        val currentGenre = currentList[position]
        holder.bind(currentGenre)
        holder.itemView.setOnClickListener {
            onGenreItemClickListener?.invoke(currentGenre)
        }
    }
}
