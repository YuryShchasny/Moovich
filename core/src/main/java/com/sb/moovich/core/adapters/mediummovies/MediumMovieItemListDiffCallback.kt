package com.sb.moovich.core.adapters.mediummovies

import androidx.recyclerview.widget.DiffUtil
import com.sb.moovich.domain.entity.Movie

class MediumMovieItemListDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}