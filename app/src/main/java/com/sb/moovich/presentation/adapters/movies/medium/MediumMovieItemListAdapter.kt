package com.sb.moovich.presentation.adapters.movies.medium

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import coil.clear
import coil.dispose
import coil.load
import com.sb.moovich.R
import com.sb.moovich.domain.entity.MediumMovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import com.sb.moovich.presentation.adapters.movies._short.ShortMovieItemListAdapter

class MediumMovieItemListAdapter(private val context: Context) :
    ListAdapter<MediumMovieInfo, MediumMovieItemViewHolder>(
        MediumMovieItemListDiffCallback()
    ) {
    companion object {
        val fakeList = mutableListOf<MediumMovieInfo>().apply {
            repeat(10) {
                this.add(
                    MediumMovieInfo(
                        0,
                        "",
                        "",
                        0.0,
                        "",
                        0,
                        0,
                        listOf()
                    ),
                )
            }
        }.toList()
    }

    var onMovieItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumMovieItemViewHolder {
        val inflater = LayoutInflater.from(context)
        return MediumMovieItemViewHolder(
            inflater.inflate(
                R.layout.item_medium_movie_card,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MediumMovieItemViewHolder, position: Int) {
        val currentMovie = getItem(position)
        if (currentMovie.poster.isNotEmpty()) {
            holder.imageViewMoviePoster.load(currentMovie.poster) {
                crossfade(true)
            }
        }
        else {
            holder.imageViewMoviePoster.dispose()
            holder.imageViewMoviePoster.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.poster_placeholder))
        }
        holder.textViewMovieName.text = currentMovie.name
        holder.textViewDescription.text = currentMovie.description
        getLength(currentMovie.movieLength, holder.textViewLength)
        holder.textViewGenres.text = currentMovie.genres.filterNotNull().joinToString(separator = ", ")
        if (currentMovie.year != 0) holder.textViewYear.text = currentMovie.year.toString()
        if (currentMovie.rating == 0.0) {
            holder.textViewMovieRating.visibility = View.GONE
        } else {
            holder.textViewMovieRating.text = String.format("%.1f", currentMovie.rating)
        }

        onMovieItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.invoke(currentMovie.id)
            }
        }
    }

    // TODO DATA BINDING
    private fun getLength(length: Int, textView: TextView) {
        if (length <= 0) textView.visibility = View.GONE
        val hoursChar = textView.context.getString(R.string.hours)
        val minutesChar = textView.context.getString(R.string.minutes)
        textView.text = "${length / 60} $hoursChar ${length % 60} $minutesChar"
    }


}