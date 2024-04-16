package com.sb.moovich.presentation.home.movie_info

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sb.moovich.R
import com.sb.moovich.databinding.FragmentMovieInfoBinding
import com.sb.moovich.di.MoovichApplication
import com.sb.moovich.di.ViewModelFactory
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.presentation.adapters.actors.ActorItemListAdapter
import com.sb.moovich.presentation.adapters.movies.MovieItemListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieInfoFragment : Fragment() {
    companion object {
        private const val MOVIE_ID = "movie_id"

        fun getBundle(movieId: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, movieId)
            return bundle
        }
    }

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MoovichApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieInfoViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        arguments?.let {
            viewModel.getMovieById(it.getInt(MOVIE_ID))
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
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

    private fun observeBookmark(currencyMovie: MovieInfo) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.bookmarkChecked.collect {isChecked ->
                    isChecked?.let {
                        if(it) {
                            binding.imageViewBookmark.setImageResource(R.drawable.bookmark_anim)
                            viewModel.addMovieToWatchList(currencyMovie)
                        }
                        else {
                            binding.imageViewBookmark.setImageResource(R.drawable.ic_bookmark)
                            viewModel.deleteMovieFromWatchList(currencyMovie)
                        }
                    }
                }
            }
        }
    }

    private fun setButtonSeeAllClickListener(
        state: MovieInfoFragmentState.Content,
        adapter: ActorItemListAdapter
    ) {
        binding.textViewSeeAll.setOnClickListener {
            state.seeAllActors = when (state.seeAllActors) {
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
        val actorAdapter = ActorItemListAdapter(requireContext())
        setButtonSeeAllClickListener(state, actorAdapter)
        actorAdapter.submitList(state.currencyMovie.actors.take(6))
        binding.recyclerViewActors.adapter = actorAdapter
        if (state.currencyMovie.similarMovies.isEmpty()) {
            binding.textViewSimilarMovies.visibility = View.GONE
        } else {
            val similarMoviesAdapter = MovieItemListAdapter(requireContext())
            similarMoviesAdapter.onMovieItemClickListener = { movieId ->
                findNavController().navigate(
                    R.id.movieInfoFragment,
                    getBundle(movieId)
                )
            }
            binding.recyclerViewSimilarMovies.adapter = similarMoviesAdapter
            similarMoviesAdapter.submitList(state.currencyMovie.similarMovies)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}