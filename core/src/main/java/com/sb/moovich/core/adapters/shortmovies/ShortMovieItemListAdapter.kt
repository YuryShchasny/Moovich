package com.sb.moovich.core.adapters.shortmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.sb.moovich.core.R
import java.util.Locale

class ShortMovieItemListAdapter : ListAdapter<ShortMovieInfo, ShortMovieItemViewHolder>(
        ShortMovieItemListDiffCallback(),
    ) {
    companion object {
        val fakeList =
            mutableListOf<ShortMovieInfo>()
                .apply {
                    repeat(10) {
                        ShortMovieInfo(
                            0,
                            "",
                            0.0,
                            "",
                        )
                    }
                }.toList()
    }

    var onMovieItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShortMovieItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ShortMovieItemViewHolder(
            inflater.inflate(
                R.layout.item_short_movie_card,
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: ShortMovieItemViewHolder,
        position: Int,
    ) {
        val context = holder.itemView.context
        val currentMovie = getItem(position)
        if(currentMovie.poster.isNotBlank()) {
            holder.imageViewMoviePoster.load(currentMovie.poster)
        }
        else {
            holder.imageViewMoviePoster.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder_movie_card))
        }
        holder.textViewMovieName.text = currentMovie.name
        if (currentMovie.rating == 0.0) {
            holder.textViewMovieRating.visibility = View.GONE
        } else {
            holder.textViewMovieRating.text = String.format(Locale.getDefault(),"%.1f", currentMovie.rating)
        }

        onMovieItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.invoke(currentMovie.id)
            }
        }
    }
}
