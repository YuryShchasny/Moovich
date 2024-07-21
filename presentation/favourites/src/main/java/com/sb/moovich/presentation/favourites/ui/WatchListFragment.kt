package com.sb.moovich.presentation.favourites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sb.moovich.core.adapters.mediummovies.MediumMovieInfo
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.favourites.adapter.Genre
import com.sb.moovich.presentation.favourites.adapter.GenreItemListAdapter
import com.sb.moovich.presentation.favourites.databinding.FragmentWatchListBinding
import com.sb.moovich.presentation.favourites.viewmodel.WatchListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WatchListFragment : BaseFragment<FragmentWatchListBinding>() {
    @Inject lateinit var navigation: INavigation
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
        initRecyclerViews()
        viewModel.getMovies()
        observeWatchList()
    }

    private fun observeWatchList() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { state ->
                    when (state) {
                        is WatchListFragmentState.Content -> {
                            binding.progressBar.visibility = View.GONE
                            setClickListener()
                            moviesAdapter.submitList(state.movieList.map {
                                MediumMovieInfo(
                                    it.id,
                                    it.name,
                                    it.description,
                                    it.rating,
                                    it.poster,
                                    it.year,
                                    it.movieLength,
                                    it.genres
                                )
                            })
                            val genresList = mutableSetOf<String>()
                            state.movieList.forEach {
                                genresList.addAll(it.genres)
                            }
                            genresAdapter.submitList(genresList.map { Genre(it, false) })
                            genresAdapter.onGenreItemClickListener = { genreContainer, position ->
                                genreContainer.isChecked = !genreContainer.isChecked
                                genresAdapter.notifyItemChanged(position)
                                var filteredMovieList = state.movieList
                                genresAdapter.currentList.forEach { genre ->
                                    if (genre.isChecked) {
                                        filteredMovieList =
                                            filteredMovieList.filter { it.genres.contains(genre.name) }
                                    }
                                }
                                moviesAdapter.submitList(filteredMovieList.map {
                                    MediumMovieInfo(
                                        it.id,
                                        it.name,
                                        it.description,
                                        it.rating,
                                        it.poster,
                                        it.year,
                                        it.movieLength,
                                        it.genres
                                    )
                                })
                            }
                        }

                        is WatchListFragmentState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerViewWatchList.visibility = View.GONE
                            binding.layoutErrorState.visibility = View.VISIBLE
                            binding.textViewErrorMessage.text =
                                ContextCompat.getString(requireContext(), state.msgResId)
                        }

                        WatchListFragmentState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
        }
    }

    private fun initRecyclerViews() {
        binding.recyclerViewWatchList.adapter = moviesAdapter
        binding.recyclerViewGenres.adapter = genresAdapter
    }

    private fun setClickListener() {
        moviesAdapter.onMovieItemClickListener = { movieId ->
            navigation.navigateToMovie(movieId)
        }
    }
}
