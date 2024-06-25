package com.sb.moovich.presentation.favourites.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.favourites.R

class GenreItemViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view) {
    val textViewGenre: TextView = view.findViewById(R.id.textViewGenre)
}
