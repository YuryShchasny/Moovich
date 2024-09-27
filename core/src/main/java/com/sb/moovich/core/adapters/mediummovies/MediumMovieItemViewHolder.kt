package com.sb.moovich.core.adapters.mediummovies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sb.moovich.core.R
import com.sb.moovich.core.databinding.ItemMediumMovieCardBinding
import com.sb.moovich.domain.entity.Movie
import java.util.Locale

class MediumMovieItemViewHolder(private val binding: ItemMediumMovieCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.imageViewMoviePoster.load(movie.poster)
        binding.textViewMovieName.text = movie.name
        binding.textViewDescription.text = movie.description
        setMovieLength(binding.textViewLength, movie.movieLength)
        binding.textViewGenres.text = movie.genres.joinToString { it }
        if (movie.year != 0) binding.textViewYear.text = movie.year.toString()
        if (movie.rating == 0.0) {
            binding.textViewMovieRating.visibility = View.GONE
        } else {
            binding.textViewMovieRating.text =
                String.format(Locale.getDefault(), "%.1f", movie.rating)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieLength(textView: TextView, length: Int) {
        if (length <= 0) textView.visibility = View.GONE
        val hoursChar = textView.context.getString(R.string.hours)
        val minutesChar = textView.context.getString(R.string.minutes)
        textView.text = "${length / 60} $hoursChar ${length % 60} $minutesChar"
    }

    companion object {
        fun from(parent: ViewGroup): MediumMovieItemViewHolder {
            return MediumMovieItemViewHolder(
                ItemMediumMovieCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
