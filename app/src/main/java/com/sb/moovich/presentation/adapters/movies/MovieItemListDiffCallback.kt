package com.sb.moovich.presentation.adapters.movies

import androidx.recyclerview.widget.DiffUtil
import com.sb.moovich.domain.entity.ShortMovieInfo

class MovieItemListDiffCallback : DiffUtil.ItemCallback<ShortMovieInfo>() {
    override fun areItemsTheSame(oldItem: ShortMovieInfo, newItem: ShortMovieInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShortMovieInfo, newItem: ShortMovieInfo): Boolean {
        return oldItem == newItem
    }
}