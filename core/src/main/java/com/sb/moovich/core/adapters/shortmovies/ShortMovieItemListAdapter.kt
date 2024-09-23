package com.sb.moovich.core.adapters.shortmovies

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ShortMovieItemListAdapter :
    ListAdapter<ShortMovie, ShortMovieItemViewHolder>(ShortMovieItemListDiffCallback()) {
    var onMovieItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortMovieItemViewHolder {
        return ShortMovieItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: ShortMovieItemViewHolder,
        position: Int,
    ) {
        val item = currentList[position]
        holder.bind(
            item,
            onClickListener = {
                onMovieItemClickListener?.invoke(item.id)
            })
    }
}
