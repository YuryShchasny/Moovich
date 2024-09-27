package com.sb.moovich.presentation.favourites.adapter

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.favourites.databinding.ItemGenreBinding

class GenreItemViewHolder(
    private val binding: ItemGenreBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre) {
        binding.textViewGenre.text = genre.name
        val color =
            if (genre.isChecked) com.sb.moovich.core.R.color.secondary else com.sb.moovich.core.R.color.black
        (binding.textViewGenre.background as GradientDrawable).color =
            ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, color))
    }

    companion object {
        fun from(parent: ViewGroup): GenreItemViewHolder {
            return GenreItemViewHolder(
                ItemGenreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
