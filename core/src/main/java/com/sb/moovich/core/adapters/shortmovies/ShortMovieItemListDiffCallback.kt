package com.sb.moovich.core.adapters.shortmovies

import androidx.recyclerview.widget.DiffUtil

class ShortMovieItemListDiffCallback : DiffUtil.ItemCallback<ShortMovie>() {
    override fun areItemsTheSame(
        oldItem: ShortMovie,
        newItem: ShortMovie,
    ): Boolean {
        return if (oldItem is ShortMovie.ShortMovieInfo && newItem is ShortMovie.ShortMovieInfo) {
            oldItem.id == newItem.id
        } else oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: ShortMovie,
        newItem: ShortMovie,
    ): Boolean {
        return if (oldItem is ShortMovie.ShortMovieInfo && newItem is ShortMovie.ShortMovieInfo) {
            oldItem == newItem
        } else oldItem == newItem
    }
}
