package com.sb.moovich.presentation.search.adapter

import androidx.recyclerview.widget.DiffUtil

class GenreListDiffCallback : DiffUtil.ItemCallback<GenreItem>() {
    override fun areItemsTheSame(
        oldItem: GenreItem,
        newItem: GenreItem,
    ): Boolean = oldItem.genre == newItem.genre

    override fun areContentsTheSame(
        oldItem: GenreItem,
        newItem: GenreItem,
    ): Boolean = oldItem == newItem
}
