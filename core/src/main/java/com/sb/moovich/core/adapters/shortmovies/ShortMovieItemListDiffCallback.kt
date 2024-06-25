package com.sb.moovich.core.adapters.shortmovies

import androidx.recyclerview.widget.DiffUtil

class ShortMovieItemListDiffCallback : DiffUtil.ItemCallback<ShortMovieInfo>() {
    override fun areItemsTheSame(
        oldItem: ShortMovieInfo,
        newItem: ShortMovieInfo,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ShortMovieInfo,
        newItem: ShortMovieInfo,
    ): Boolean = oldItem == newItem
}
