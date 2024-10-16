package com.sb.moovich.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sb.moovich.core.extensions.loadCoil
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.home.databinding.ItemCarouselBinding

class MovieCarouselAdapter(private val movies: List<Movie>, private val onClickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieCarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCarouselViewHolder =
        MovieCarouselViewHolder.from(parent)

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: MovieCarouselViewHolder, position: Int) {
        if(movies.isEmpty()) return
        val currentPosition = position % movies.size
        holder.bind(movies[currentPosition], currentPosition, onClickListener)
    }
}

class MovieCarouselViewHolder(private val binding: ItemCarouselBinding) : ViewHolder(binding.root) {

    fun bind(movie: Movie, position: Int, onClickListener: (Movie) -> Unit) {
        binding.image.loadCoil(movie.poster) {
            binding.numberView.setBitmap(it.toBitmap())
        }
        binding.card.setOnClickListener { onClickListener(movie) }
        binding.numberView.text = (position + 1).toString()
    }

    companion object {
        fun from(parent: ViewGroup): MovieCarouselViewHolder {
            return MovieCarouselViewHolder(
                ItemCarouselBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}