package com.sb.moovich.presentation.adapters.movies._short

import androidx.recyclerview.widget.DiffUtil
import com.sb.moovich.domain.entity.ShortMovieInfo

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
