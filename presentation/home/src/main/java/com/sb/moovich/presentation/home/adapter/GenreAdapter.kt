package com.sb.moovich.presentation.home.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.extensions.dpToPx
import com.sb.moovich.presentation.home.databinding.ItemHomeGenreBinding

class GenreAdapter(private val genres: List<String>, private val onClickListener: (String) -> Unit) : RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder.from(parent)

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.bind(genre, position)
        holder.itemView.setOnClickListener { onClickListener(genre) }
    }
}

class GenreViewHolder(val binding: ItemHomeGenreBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(genre: String, position: Int) {
        val color =
            if (position % 4 == 3) com.sb.moovich.core.R.color.primary
            else if (position % 4 == 0) com.sb.moovich.core.R.color.secondary
            else com.sb.moovich.core.R.color.white
        binding.root.background = (binding.root.background as GradientDrawable).apply {
            setStroke(2.dpToPx(), ContextCompat.getColor(binding.root.context, color))
        }
        binding.genre.setTextColor(ContextCompat.getColor(binding.root.context, color))
        binding.genre.text = genre
    }

    companion object {
        fun from(parent: ViewGroup): GenreViewHolder {
            return GenreViewHolder(
                ItemHomeGenreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}