package com.sb.moovich.core.adapters.shortmovies

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ShortMovieItemListAdapter :
    ListAdapter<ShortMovie, ViewHolder>(ShortMovieItemListDiffCallback()) {
    var onMovieItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_MOVIE -> ShortMovieItemViewHolder.from(parent)
            TYPE_SHIMMER -> ShortMovieShimmerViewHolder.from(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (val item = currentList[position]) {
            is ShortMovie.ShortMovieInfo -> (holder as ShortMovieItemViewHolder).bind(
                item,
                onClickListener = {
                    onMovieItemClickListener?.invoke(item.id)
                })

            is ShortMovie.Shimmer -> (holder as ShortMovieShimmerViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ShortMovie.ShortMovieInfo -> TYPE_MOVIE
            is ShortMovie.Shimmer -> TYPE_SHIMMER
        }
    }

    companion object {
        private const val TYPE_MOVIE = 0
        private const val TYPE_SHIMMER = 1
    }
}
