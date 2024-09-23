package com.sb.moovich.core.adapters.mediummovies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sb.moovich.core.R
import com.sb.moovich.core.extensions.dpToPx
import com.sb.moovich.domain.entity.Movie
import java.util.Locale

class MediumMovieItemListAdapter :
    ListAdapter<Movie, MediumMovieItemViewHolder>(
        MediumMovieItemListDiffCallback()
    ) {
    var onMovieItemClickListener: ((Int) -> Unit)? = null
    var topMargin = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumMovieItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MediumMovieItemViewHolder(
            inflater.inflate(
                R.layout.item_medium_movie_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediumMovieItemViewHolder, position: Int) {
        val context = holder.itemView.context
        val currentMovie = getItem(position)
        if (currentMovie.poster.isNotBlank()) {
            holder.imageViewMoviePoster.load(currentMovie.poster)
        } else {
            holder.imageViewMoviePoster.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.poster_placeholder
                )
            )
        }
        holder.textViewMovieName.text = currentMovie.name
        holder.textViewDescription.text = currentMovie.description
        getLength(currentMovie.movieLength, holder.textViewLength)
        holder.textViewGenres.text =
            currentMovie.genres.joinToString(separator = ", ")
        if (currentMovie.year != 0) holder.textViewYear.text = currentMovie.year.toString()
        if (currentMovie.rating == 0.0) {
            holder.textViewMovieRating.visibility = View.GONE
        } else {
            holder.textViewMovieRating.text =
                String.format(Locale.getDefault(), "%.1f", currentMovie.rating)
        }

        onMovieItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.invoke(currentMovie.id)
            }
        }

        holder.itemView.updateLayoutParams<RecyclerView.LayoutParams> {
            setMargins(0, (if(position == 0) this@MediumMovieItemListAdapter.topMargin else 0) + 8.dpToPx(), 0, 8.dpToPx())
        }
    }

    // TODO DATA BINDING
    @SuppressLint("SetTextI18n")
    private fun getLength(length: Int, textView: TextView) {
        if (length <= 0) textView.visibility = View.GONE
        val hoursChar = textView.context.getString(R.string.hours)
        val minutesChar = textView.context.getString(R.string.minutes)
        textView.text = "${length / 60} $hoursChar ${length % 60} $minutesChar"
    }
}