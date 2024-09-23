package com.sb.moovich.core.adapters.shortmovies

import androidx.recyclerview.widget.DiffUtil

class ShortMovieItemListDiffCallback : DiffUtil.ItemCallback<ShortMovie>() {
    override fun areItemsTheSame(
        oldItem: ShortMovie,
        newItem: ShortMovie,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ShortMovie,
        newItem: ShortMovie,
    ): Boolean {
        return oldItem == newItem
    }
}
