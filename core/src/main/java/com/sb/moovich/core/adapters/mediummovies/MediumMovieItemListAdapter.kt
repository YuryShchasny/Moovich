package com.sb.moovich.core.adapters.mediummovies

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.domain.entity.Movie

class MediumMovieItemListAdapter :
    ListAdapter<MediumMovie, RecyclerView.ViewHolder>(MediumMovieItemListDiffCallback()) {
    var onMovieItemClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            INFO_TYPE -> MediumMovieItemViewHolder.from(parent)
            SHIMMER_TYPE -> MediumMovieShimmerViewHolder.from(parent)
            else -> throw IllegalArgumentException()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val movie = currentList[position]) {
            is MediumMovie.Info -> {
                (holder as MediumMovieItemViewHolder).bind(movie.movie)
                onMovieItemClickListener?.let { listener ->
                    holder.itemView.setOnClickListener {
                        listener.invoke(movie.movie)
                    }
                }
            }

            MediumMovie.Shimmer -> (holder as MediumMovieShimmerViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is MediumMovie.Info -> INFO_TYPE
            MediumMovie.Shimmer -> SHIMMER_TYPE
        }
    }

    fun submit(list: List<Movie>, withShimmer: Boolean = false) {
        val mappedList = list.map { MediumMovie.Info(it) }
        val resultList = if(withShimmer) mappedList + MediumMovie.Shimmer else mappedList
        submitList(resultList)
    }

    fun submit(list: List<Movie>, withShimmer: Boolean = false, commitCallback: Runnable?) {
        val mappedList = list.map { MediumMovie.Info(it) }
        val resultList = if(withShimmer) mappedList + MediumMovie.Shimmer else mappedList
        submitList(resultList, commitCallback)
    }

    companion object {
        private const val INFO_TYPE = 0
        private const val SHIMMER_TYPE = 1
    }
}
