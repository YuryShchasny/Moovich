package com.sb.moovich.presentation.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class GenreListAdapter : ListAdapter<GenreItem, GenreViewHolder>(GenreListDiffCallback()) {

    var onItemClickListener: ((GenreItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder.from(parent)

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val currentGenre = currentList[position]
        holder.bind(currentGenre, onItemClickListener)
    }
}

data class GenreItem(val genre: String, val isChecked: Boolean)
