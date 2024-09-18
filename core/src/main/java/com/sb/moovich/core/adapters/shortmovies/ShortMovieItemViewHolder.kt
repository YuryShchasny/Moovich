package com.sb.moovich.core.adapters.shortmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sb.moovich.core.R
import com.sb.moovich.core.databinding.ItemShortMovieCardBinding
import com.sb.moovich.core.databinding.ItemShortMovieShimmerBinding
import java.util.Locale

class ShortMovieItemViewHolder(
    private val binding: ItemShortMovieCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShortMovie.ShortMovieInfo, onClickListener: () -> Unit) {
        if(item.poster.isNotBlank()) {
            binding.imageViewMoviePoster.load(item.poster)
        }
        else {
            binding.imageViewMoviePoster.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.placeholder_movie_card))
        }
        binding.textViewMovieName.text = item.name
        if (item.rating == 0.0) {
            binding.textViewMovieRating.visibility = View.GONE
        } else {
            binding.textViewMovieRating.text = String.format(Locale.getDefault(),"%.1f", item.rating)
        }
        binding.root.setOnClickListener {
            onClickListener.invoke()
        }
    }

    companion object {
        fun from(parent: ViewGroup): ShortMovieItemViewHolder {
            return ShortMovieItemViewHolder(
                ItemShortMovieCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}
