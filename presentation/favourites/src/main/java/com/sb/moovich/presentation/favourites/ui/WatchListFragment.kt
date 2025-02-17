package com.sb.moovich.presentation.favourites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sb.moovich.core.R
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.favourites.adapter.Genre
import com.sb.moovich.presentation.favourites.adapter.GenreItemListAdapter
import com.sb.moovich.presentation.favourites.databinding.FragmentWatchListBinding
import com.sb.moovich.presentation.favourites.ui.model.WatchListFragmentEvent
import com.sb.moovich.presentation.favourites.ui.model.WatchListFragmentState
import com.sb.moovich.presentation.favourites.viewmodel.WatchListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : BaseFragment<FragmentWatchListBinding>() {

    private val viewModel: WatchListViewModel by viewModels()
    private val moviesAdapter = MediumMovieItemListAdapter()
    private val genresAdapter = GenreItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWatchListBinding {
        return FragmentWatchListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        initRecyclerViews()
        setClickListener()
        observeWatchList()
    }

    private fun observeWatchList() {
        collectWithLifecycle(viewModel.state) { state ->
            when (state) {
                is WatchListFragmentState.Content -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutEmptyContent.visibility = View.GONE
                    binding.recyclerViewGenres.visibility = View.VISIBLE
                    binding.recyclerViewWatchList.visibility = View.VISIBLE
                    moviesAdapter.submit(filterMovies(state.movieList, state.genres))
                    genresAdapter.submitList(state.genres)
                }

                is WatchListFragmentState.EmptyContent -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewWatchList.visibility = View.GONE
                    binding.recyclerViewGenres.visibility = View.GONE
                    binding.layoutEmptyContent.visibility = View.VISIBLE
                    binding.textViewErrorMessage.text =
                        getStringCompat(R.string.error_empty_watch_list)
                }

                WatchListFragmentState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun filterMovies(movies: List<Movie>, genres: List<Genre>): List<Movie> {
        var filteredMovieList = movies
        genres.forEach { genre ->
            if (genre.isChecked)
                filteredMovieList = filteredMovieList.filter { it.genres.contains(genre.name) }
        }
        return filteredMovieList
    }

    private fun initRecyclerViews() {
        binding.recyclerViewWatchList.adapter = moviesAdapter
        binding.recyclerViewGenres.adapter = genresAdapter
    }

    private fun setClickListener() {
        moviesAdapter.onMovieItemClickListener = { movie ->
            viewModel.fetchEvent(WatchListFragmentEvent.OnMovieClick(movie))
        }
        genresAdapter.onGenreItemClickListener = { genre ->
            viewModel.fetchEvent(WatchListFragmentEvent.OnGenreClick(genre))
        }
    }
}
