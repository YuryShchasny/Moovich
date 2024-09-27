package com.sb.moovich.presentation.home.adapter

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.home.databinding.ItemCarouselBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private val asyncLoaderScope = CoroutineScope(Dispatchers.IO)
    private val asyncLoadJob: Job? = null

    fun bind(movie: Movie, position: Int, onClickListener: (Movie) -> Unit) {
        val context = binding.root.context
        binding.image.load(movie.poster)
        binding.card.setOnClickListener { onClickListener(movie) }
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(movie.poster)
            .build()
        asyncLoadJob?.cancel()
        asyncLoaderScope.launch {
            val result = imageLoader.execute(request)
            if (result is SuccessResult) {
                withContext(Dispatchers.Main) {
                    val bitmap = (result.drawable as BitmapDrawable).bitmap
                    binding.numberView.setBitmap(bitmap)
                }
            }
        }
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