package com.sb.moovich.core.adapters.shortmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sb.moovich.core.databinding.ItemShortMovieCardBinding
import java.util.Locale

class ShortMovieItemViewHolder(
    private val binding: ItemShortMovieCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ShortMovie, onClickListener: () -> Unit) {
        if(item.poster.isNotBlank()) {
            binding.imageViewMoviePoster.load(item.poster)
        }
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
