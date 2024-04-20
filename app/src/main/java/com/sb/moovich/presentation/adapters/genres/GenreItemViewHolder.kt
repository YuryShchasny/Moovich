package com.sb.moovich.presentation.adapters.genres

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.R

class GenreItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewGenre: TextView = view.findViewById(R.id.textViewGenre)
}