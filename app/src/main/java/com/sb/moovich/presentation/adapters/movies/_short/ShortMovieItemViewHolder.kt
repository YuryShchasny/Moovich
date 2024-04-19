package com.sb.moovich.presentation.adapters.movies._short

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.R

class ShortMovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageViewMoviePoster: ImageView = view.findViewById(R.id.imageViewMoviePoster)
    val textViewMovieName: TextView = view.findViewById(R.id.textViewMovieName)
    val textViewMovieRating: TextView = view.findViewById(R.id.textViewMovieRating)

}