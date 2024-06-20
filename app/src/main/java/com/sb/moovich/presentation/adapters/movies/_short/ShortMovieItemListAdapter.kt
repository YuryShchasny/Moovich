package com.sb.moovich.presentation.adapters.movies._short

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.sb.moovich.R
import com.sb.moovich.domain.entity.ShortMovieInfo
import java.util.Locale

class ShortMovieItemListAdapter(
    private val context: Context,
) : ListAdapter<ShortMovieInfo, ShortMovieItemViewHolder>(
        ShortMovieItemListDiffCallback(),
    ) {
    companion object {
        val fakeList =
            mutableListOf<ShortMovieInfo>()
                .apply {
                    repeat(10) {
                        this.add(
                            ShortMovieInfo(
                                0,
                                "",
                                0.0,
                                "",
                            ),
                        )
                    }
                }.toList()
    }

    var onMovieItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShortMovieItemViewHolder {
        val inflater = LayoutInflater.from(context)
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
        val currentMovie = getItem(position)
        if (currentMovie.previewUrl.isNotEmpty()) {
            holder.imageViewMoviePoster.load(currentMovie.previewUrl) {
                crossfade(true)
            }
        } else {
            holder.imageViewMoviePoster.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.poster_placeholder,
                ),
            )
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
