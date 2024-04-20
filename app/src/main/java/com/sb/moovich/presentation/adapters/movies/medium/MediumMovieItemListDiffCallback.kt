package com.sb.moovich.presentation.adapters.movies.medium

import androidx.recyclerview.widget.DiffUtil
import com.sb.moovich.domain.entity.MediumMovieInfo

class MediumMovieItemListDiffCallback : DiffUtil.ItemCallback<MediumMovieInfo>() {
    override fun areItemsTheSame(oldItem: MediumMovieInfo, newItem: MediumMovieInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediumMovieInfo, newItem: MediumMovieInfo): Boolean {
        return oldItem == newItem
    }
}