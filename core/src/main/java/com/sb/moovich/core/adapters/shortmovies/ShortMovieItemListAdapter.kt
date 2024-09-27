package com.sb.moovich.core.adapters.shortmovies

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sb.moovich.domain.entity.Movie

class ShortMovieItemListAdapter :
    ListAdapter<Movie, ShortMovieItemViewHolder>(ShortMovieItemListDiffCallback()) {
    var onMovieItemClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortMovieItemViewHolder {
        return ShortMovieItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: ShortMovieItemViewHolder,
        position: Int,
    ) {
        val movie = currentList[position]
        holder.bind(
            movie,
            onClickListener = {
                onMovieItemClickListener?.invoke(movie)
            })
    }
}
