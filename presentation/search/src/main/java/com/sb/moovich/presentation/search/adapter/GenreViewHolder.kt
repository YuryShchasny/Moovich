package com.sb.moovich.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.search.databinding.ItemGenreCheckableBinding

class GenreViewHolder(
    private val binding: ItemGenreCheckableBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genreItem: GenreItem, onClick: ((GenreItem) -> Unit)?) {
        binding.checkboxGenreTextView.text = genreItem.genre
        binding.checkboxGenre.isChecked = genreItem.isChecked
        binding.genreLayout.setOnClickListener {
            onClick?.invoke(genreItem)
        }
    }

    companion object {
        fun from(parent: ViewGroup): GenreViewHolder {
            return GenreViewHolder(
                ItemGenreCheckableBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
