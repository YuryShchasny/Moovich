package com.sb.moovich.presentation.adapters.genres

import androidx.recyclerview.widget.DiffUtil

class GenreItemListDiffCallback : DiffUtil.ItemCallback<GenreContainer>() {
    override fun areItemsTheSame(
        oldItem: GenreContainer,
        newItem: GenreContainer,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: GenreContainer,
        newItem: GenreContainer,
    ): Boolean = oldItem == newItem
}
