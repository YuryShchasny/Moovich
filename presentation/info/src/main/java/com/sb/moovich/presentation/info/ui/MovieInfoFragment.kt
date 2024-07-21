package com.sb.moovich.presentation.info.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sb.moovich.core.adapters.shortmovies.ShortMovieInfo
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.info.R
import com.sb.moovich.presentation.info.adapter.actors.ActorItemListAdapter
import com.sb.moovich.presentation.info.databinding.FragmentMovieInfoBinding
import com.sb.moovich.presentation.info.viewmodel.MovieInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieInfoFragment : BaseFragment<FragmentMovieInfoBinding>() {
    @Inject lateinit var navigation: INavigation
    private val viewModel: MovieInfoViewModel by viewModels()
    private val actorAdapter = ActorItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieInfoBinding {
        return FragmentMovieInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        arguments?.let {
            viewModel.getMovieById(it.getInt("movie_id"))
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is MovieInfoFragmentState.Content -> {
                            binding.progressBar.visibility = View.GONE
                            binding.scrollView.visibility = View.VISIBLE
                            binding.movie = state.currencyMovie
                            observeBookmark(state.currencyMovie)
                            setButtonWatchClickListener(state)
                            setAdapters(state)
                            binding.imageViewBookmark.setOnClickListener {
                                viewModel.reverseBookmarkValue()
                            }
                        }

                        is MovieInfoFragmentState.Error -> {
                            Toast.makeText(requireContext(), state.msg, Toast.LENGTH_SHORT).show()
                        }

                        MovieInfoFragmentState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.scrollView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun observeBookmark(currencyMovie: Movie) {
        lifecycleScope.launch {
            viewModel.bookmarkChecked
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { isChecked ->
                    isChecked?.let {
                        if (it) {
                            binding.imageViewBookmark.setImageResource(R.drawable.bookmark_anim)
                            viewModel.addMovieToWatchList(currencyMovie)
                        } else {
                            binding.imageViewBookmark.setImageResource(com.sb.moovich.core.R.drawable.ic_bookmark)
                            viewModel.deleteMovieFromWatchList(currencyMovie)
                        }
                    }
                }
        }
    }

    private fun setButtonSeeAllClickListener(
        state: MovieInfoFragmentState.Content,
        adapter: ActorItemListAdapter,
    ) {
        binding.textViewSeeAll.setOnClickListener {
            state.seeAllActors =
                when (state.seeAllActors) {
                    true -> {
                        adapter.submitList(state.currencyMovie.actors.take(6))
                        false
                    }

                    false -> {
                        adapter.submitList(state.currencyMovie.actors)
                        true
                    }
                }
        }
    }

    private fun setAdapters(state: MovieInfoFragmentState.Content) {
        setButtonSeeAllClickListener(state, actorAdapter)
        actorAdapter.submitList(state.currencyMovie.actors.take(6))
        binding.recyclerViewActors.adapter = actorAdapter
        if (state.currencyMovie.similarMovies.isEmpty()) {
            binding.textViewSimilarMovies.visibility = View.GONE
        } else {
            val similarMoviesAdapter = ShortMovieItemListAdapter()
            similarMoviesAdapter.onMovieItemClickListener = { movieId ->
               navigation.navigateToMovie(movieId)
            }
            binding.recyclerViewSimilarMovies.adapter = similarMoviesAdapter
            similarMoviesAdapter.submitList(state.currencyMovie.similarMovies.map {
                ShortMovieInfo(it.id, it.name, it.rating, it.poster)
            })
        }
    }

    private fun setButtonWatchClickListener(state: MovieInfoFragmentState.Content) {
        binding.buttonWatch.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(state.currencyMovie.urlWatch)
                startActivity(this)
            }
        }
    }
}
