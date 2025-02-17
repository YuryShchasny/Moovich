package com.sb.moovich.core.adapters.shortmovies

import androidx.recyclerview.widget.DiffUtil
import com.sb.moovich.domain.entity.Movie

class ShortMovieItemListDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
