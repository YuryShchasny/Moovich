package com.sb.moovich.presentation.search.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.presentation.search.R

class GenreViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view) {
    val genreTextView: TextView = view.findViewById(R.id.checkboxGenreTextView)
    val genreCheckBox: CheckBox = view.findViewById(R.id.checkboxGenre)
}
