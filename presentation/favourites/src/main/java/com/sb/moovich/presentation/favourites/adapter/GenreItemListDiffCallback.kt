package com.sb.moovich.presentation.favourites.adapter

import androidx.recyclerview.widget.DiffUtil

class GenreItemListDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(
        oldItem: Genre,
        newItem: Genre,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: Genre,
        newItem: Genre,
    ): Boolean = oldItem == newItem
}
