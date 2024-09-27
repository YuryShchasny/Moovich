package com.sb.moovich.presentation.info.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.domain.entity.Actor
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.info.adapter.actors.ActorItemListAdapter
import com.sb.moovich.presentation.info.databinding.FragmentMovieInfoBinding
import com.sb.moovich.presentation.info.ui.model.MovieInfoFragmentEvent
import com.sb.moovich.presentation.info.ui.model.MovieInfoFragmentState
import com.sb.moovich.presentation.info.viewmodel.MovieInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieInfoFragment : BaseFragment<FragmentMovieInfoBinding>() {

    private val viewModel: MovieInfoViewModel by viewModels()
    private val actorAdapter = ActorItemListAdapter()
    private val similarMoviesAdapter = ShortMovieItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieInfoBinding {
        return FragmentMovieInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            viewModel.getMovieById(it.getInt("movie_id"))
        }
        observeState()
    }

    private fun observeState() {
        collectWithLifecycle(viewModel.state, Lifecycle.State.CREATED) { state ->
            when (state) {
                is MovieInfoFragmentState.Content -> {
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    binding.movie = state.movie
                    binding.descriptionTitle.visibility =
                        if (state.movie.description.isEmpty()) View.GONE else View.VISIBLE
                    binding.actorsTitle.visibility =
                        if (state.movie.actors.isEmpty()) View.GONE else View.VISIBLE
                    setClickListeners(state)
                    setAdapters()
                    setActors(state.movie.actors, state.seeAllActors)
                    setSimilarMovies(state.movie.similarMovies)
                    setBookMarkView(state.bookMarkChecked)
                }

                MovieInfoFragmentState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scrollView.visibility = View.GONE
                }
            }
        }
    }

    private fun setBookMarkView(isChecked: Boolean) {
        binding.imageViewBookmark.imageTintList = ColorStateList.valueOf(
            if (isChecked) getColorCompat(com.sb.moovich.core.R.color.primary)
            else getColorCompat(com.sb.moovich.core.R.color.white)
        )
    }

    private fun setAdapters() {
        binding.recyclerViewActors.adapter = actorAdapter
        binding.recyclerViewSimilarMovies.adapter = similarMoviesAdapter
    }

    private fun setActors(actors: List<Actor>, seeAll: Boolean) {
        binding.textViewSeeAll.setTextColor(
            getColorCompat(
                if (seeAll) com.sb.moovich.core.R.color.primary
                else com.sb.moovich.core.R.color.white
            )
        )
        actorAdapter.submitList(
            if (seeAll) actors
            else actors.take(6)
        )
    }

    private fun setSimilarMovies(movies: List<Movie>) {
        if (movies.isNotEmpty()) similarMoviesAdapter.submitList(movies)
        else binding.textViewSimilarMovies.visibility = View.GONE
    }

    private fun setClickListeners(state: MovieInfoFragmentState.Content) {
        binding.buttonWatch.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(state.movie.urlWatch)
                startActivity(this)
            }
        }
        similarMoviesAdapter.onMovieItemClickListener = { movie ->
            viewModel.fetchEvent(MovieInfoFragmentEvent.OnSimilarMovieClick(movie))
        }
        binding.imageViewBookmark.setOnClickListener {
            viewModel.fetchEvent(MovieInfoFragmentEvent.OnBookmarkClick)
        }
        binding.textViewSeeAll.setOnClickListener {
            viewModel.fetchEvent(MovieInfoFragmentEvent.SeeAllActors)
        }
    }
}
