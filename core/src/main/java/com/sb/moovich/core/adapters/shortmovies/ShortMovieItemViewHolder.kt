package com.sb.moovich.core.adapters.shortmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.databinding.ItemShortMovieCardBinding
import com.sb.moovich.core.extensions.loadCoil
import com.sb.moovich.domain.entity.Movie
import java.util.Locale

class ShortMovieItemViewHolder(
    private val binding: ItemShortMovieCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie, onClickListener: () -> Unit) {
        binding.imageViewMoviePoster.loadCoil(item.poster)
        binding.textViewMovieName.text = item.name
        if (item.rating == 0.0) {
            binding.textViewMovieRating.visibility = View.GONE
        } else {
            binding.textViewMovieRating.text = String.format(Locale.getDefault(),"%.1f", item.rating)
        }
        binding.cardView.setOnClickListener {
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
