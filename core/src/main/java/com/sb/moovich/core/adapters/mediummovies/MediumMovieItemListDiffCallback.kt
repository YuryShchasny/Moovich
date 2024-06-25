package com.sb.moovich.core.adapters.mediummovies

import androidx.recyclerview.widget.DiffUtil

class MediumMovieItemListDiffCallback : DiffUtil.ItemCallback<MediumMovieInfo>() {
    override fun areItemsTheSame(oldItem: MediumMovieInfo, newItem: MediumMovieInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediumMovieInfo, newItem: MediumMovieInfo): Boolean {
        return oldItem == newItem
    }
}