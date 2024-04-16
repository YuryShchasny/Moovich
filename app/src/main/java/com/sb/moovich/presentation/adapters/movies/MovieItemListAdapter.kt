package com.sb.moovich.presentation.adapters.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.sb.moovich.R
import com.sb.moovich.domain.entity.ShortMovieInfo

class MovieItemListAdapter(private val context: Context) : ListAdapter<ShortMovieInfo, MovieItemViewHolder>(MovieItemListDiffCallback()) {

companion object {
    val fakeList = mutableListOf<ShortMovieInfo>().apply { repeat(10) {this.add(ShortMovieInfo(0, "", 0.0, ""),) } }.toList()
}

    var onMovieItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val inflater = LayoutInflater.from(context)
        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie_card, parent, false))
    }


    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val currentMovie = getItem(position)
        if(currentMovie.previewUrl.isNotEmpty()) {
            holder.imageViewMoviePoster.load(currentMovie.previewUrl) {
                crossfade(true)
            }
        }
        holder.textViewMovieName.text = currentMovie.name
        if(currentMovie.rating == 0.0) {
            holder.textViewMovieRating.visibility = View.GONE
        }
        else {
            holder.textViewMovieRating.text = String.format("%.1f", currentMovie.rating)
        }

        onMovieItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.invoke(currentMovie.id)
            }
        }
    }

}