package com.sb.moovich.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sb.moovich.presentation.search.R

class GenreListAdapter : ListAdapter<GenreItem, GenreViewHolder>(
        GenreListDiffCallback(),
    ) {

    var onItemClickListener: ((String, Boolean) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GenreViewHolder(inflater.inflate(R.layout.genre_checkable_item, parent, false))
    }

    override fun onBindViewHolder(
        holder: GenreViewHolder,
        position: Int,
    ) {
        val currentGenre = currentList[position]
        holder.genreTextView.text = currentGenre.genre
        holder.genreCheckBox.isChecked = currentGenre.isChecked
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(currentGenre.genre, !holder.genreCheckBox.isChecked)
        }
    }
}

data class GenreItem(val genre: String, val isChecked: Boolean)
