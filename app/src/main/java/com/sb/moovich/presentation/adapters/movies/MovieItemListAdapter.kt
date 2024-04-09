package com.sb.moovich.presentation.adapters.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.sb.moovich.R
import com.sb.moovich.domain.entity.ShortMovieInfo

class MovieItemListAdapter(private val context: Context) : ListAdapter<ShortMovieInfo, MovieItemViewHolder>(MovieItemListDiffCallback()) {


    var onMovieItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val inflater = LayoutInflater.from(context)
        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie_card, parent, false))
    }


    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.imageViewMoviePoster.load(currentMovie.previewUrl)
        holder.textViewMovieName.text = currentMovie.name
        holder.textViewMovieRating.text = String.format("%.1f", currentMovie.rating)

        onMovieItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.invoke(currentMovie.id)
            }
        }
    }

}