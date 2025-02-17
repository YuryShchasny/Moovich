package com.sb.moovich.core.adapters.mediummovies

import androidx.recyclerview.widget.DiffUtil

class MediumMovieItemListDiffCallback : DiffUtil.ItemCallback<MediumMovie>() {
    override fun areItemsTheSame(oldItem: MediumMovie, newItem: MediumMovie): Boolean {
        return if (oldItem is MediumMovie.Info && newItem is MediumMovie.Info) oldItem.movie.id == newItem.movie.id
        else oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: MediumMovie, newItem: MediumMovie): Boolean {
        return if (oldItem is MediumMovie.Info && newItem is MediumMovie.Info) oldItem.movie == newItem.movie
        else oldItem == newItem
    }
}