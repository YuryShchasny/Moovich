package com.sb.moovich.presentation.favourites.adapter

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.sb.moovich.presentation.favourites.R

class GenreItemListAdapter : ListAdapter<Genre, GenreItemViewHolder>(GenreItemListDiffCallback()) {
    var onGenreItemClickListener: ((Genre, Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GenreItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GenreItemViewHolder(inflater.inflate(R.layout.item_genre, parent, false))
    }

    override fun onBindViewHolder(
        holder: GenreItemViewHolder,
        position: Int,
    ) {
        val context = holder.itemView.context
        val currentGenre = currentList[position]
        holder.textViewGenre.text = currentGenre.name
        if (currentGenre.isChecked) {
            (holder.textViewGenre.background as GradientDrawable).color =
                ColorStateList.valueOf(ContextCompat.getColor(context, com.sb.moovich.core.R.color.secondary))
        } else {
            (holder.textViewGenre.background as GradientDrawable).color =
                ColorStateList.valueOf(ContextCompat.getColor(context, com.sb.moovich.core.R.color.black))
        }
        holder.itemView.setOnClickListener {
            onGenreItemClickListener?.invoke(currentGenre, position)
        }
    }
}
