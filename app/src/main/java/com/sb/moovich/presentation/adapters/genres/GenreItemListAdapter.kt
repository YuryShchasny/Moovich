package com.sb.moovich.presentation.adapters.genres

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.sb.moovich.R

class GenreItemListAdapter(private val context: Context) :
    ListAdapter<GenreContainer, GenreItemViewHolder>(GenreItemListDiffCallback()) {

    var onGenreItemClickListener: ((GenreContainer, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreItemViewHolder {
        val inflater = LayoutInflater.from(context)
        return GenreItemViewHolder(inflater.inflate(R.layout.item_genre, parent, false))
    }


    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        val currentGenre = currentList[position]
        holder.textViewGenre.text = currentGenre.name
        if (currentGenre.isChecked) {
            (holder.textViewGenre.background as GradientDrawable).color =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.secondary))
        } else {
            (holder.textViewGenre.background as GradientDrawable).color =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
        }
        holder.itemView.setOnClickListener {
            onGenreItemClickListener?.invoke(currentGenre, position)
        }

    }

}