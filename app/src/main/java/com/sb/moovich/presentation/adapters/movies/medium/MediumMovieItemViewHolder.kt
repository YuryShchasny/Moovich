package com.sb.moovich.presentation.adapters.movies.medium

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.R

class MediumMovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageViewMoviePoster: ImageView = view.findViewById(R.id.imageViewMoviePoster)
    val textViewMovieName: TextView = view.findViewById(R.id.textViewMovieName)
    val textViewMovieRating: TextView = view.findViewById(R.id.textViewMovieRating)
    val textViewGenres: TextView = view.findViewById(R.id.textViewGenres)
    val textViewYear: TextView = view.findViewById(R.id.textViewYear)
    val textViewLength: TextView = view.findViewById(R.id.textViewLength)
    val textViewDescription: TextView = view.findViewById(R.id.textViewDescription)

}