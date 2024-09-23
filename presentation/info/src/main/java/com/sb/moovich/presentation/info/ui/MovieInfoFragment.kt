package com.sb.moovich.presentation.info.ui

import android.content.Intent
import android.content.res.ColorStateList
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
import com.sb.moovich.core.adapters.shortmovies.ShortMovie
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.info.R
import com.sb.moovich.presentation.info.adapter.actors.ActorItemListAdapter
import com.sb.moovich.presentation.info.databinding.FragmentMovieInfoBinding
import com.sb.moovich.presentation.info.viewmodel.MovieInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieInfoFragment : BaseFragment<FragmentMovieInfoBinding>() {
    @Inject
    lateinit var navigation: INavigation
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
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is MovieInfoFragmentState.Content -> {
                            binding.progressBar.visibility = View.GONE
                            binding.scrollView.visibility = View.VISIBLE
                            binding.movie = state.currencyMovie
                            binding.descriptionTitle.visibility =
                                if (state.currencyMovie.description.isEmpty()) View.GONE else View.VISIBLE
                            binding.actorsTitle.visibility =
                                if (state.currencyMovie.actors.isEmpty()) View.GONE else View.VISIBLE
                            setButtonWatchClickListener(state)
                            setAdapters(state)
                            setBookMarkView(state.bookMarkChecked)
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

    private fun setBookMarkView(isChecked: Boolean) {
        binding.imageViewBookmark.imageTintList = ColorStateList.valueOf(
            if (isChecked) getColorCompat(com.sb.moovich.core.R.color.primary)
            else getColorCompat(com.sb.moovich.core.R.color.white)
        )
        binding.imageViewBookmark.setOnClickListener {
            viewModel.reverseBookmarkValue()
        }
    }

    private fun setButtonSeeAll(seeAll: Boolean) {
        binding.textViewSeeAll.setTextColor(
            getColorCompat(
                if (seeAll) com.sb.moovich.core.R.color.primary
                else com.sb.moovich.core.R.color.white
            )
        )
        binding.textViewSeeAll.setOnClickListener {
            viewModel.seeAll()
        }
    }

    private fun setAdapters(state: MovieInfoFragmentState.Content) {
        setButtonSeeAll(state.seeAllActors)
        val actors = if (state.seeAllActors) state.currencyMovie.actors
        else state.currencyMovie.actors.take(6)
        actorAdapter.submitList(actors)
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
                ShortMovie(it.id, it.name, it.rating, it.poster)
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
