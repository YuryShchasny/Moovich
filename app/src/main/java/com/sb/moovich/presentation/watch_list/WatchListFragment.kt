package com.sb.moovich.presentation.watch_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sb.moovich.R
import com.sb.moovich.databinding.FragmentWatchListBinding
import com.sb.moovich.di.MoovichApplication
import com.sb.moovich.di.ViewModelFactory
import com.sb.moovich.presentation.adapters.genres.GenreContainer
import com.sb.moovich.presentation.adapters.genres.GenreItemListAdapter
import com.sb.moovich.presentation.adapters.movies.medium.MediumMovieItemListAdapter
import com.sb.moovich.presentation.movie_info.MovieInfoFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class WatchListFragment : Fragment() {

    private var _binding: FragmentWatchListBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MoovichApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WatchListViewModel::class.java]
    }

    private val moviesAdapter by lazy {
        MediumMovieItemListAdapter(requireContext())
    }
    private val genresAdapter by lazy {
        GenreItemListAdapter(requireContext())
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
        _binding = FragmentWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()
        observeWatchList()
    }


    private fun observeWatchList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is WatchListFragmentState.Content -> {
                            setClickListener()
                            moviesAdapter.submitList(state.movieList)
                            val genresList = mutableSetOf<String>()
                            state.movieList.forEach {
                                genresList.addAll(it.genres.filterNotNull())
                            }
                            genresAdapter.submitList(genresList.map { GenreContainer(it, false) })
                            genresAdapter.onGenreItemClickListener = { genreContainer, position ->
                                genreContainer.isChecked = !genreContainer.isChecked
                                genresAdapter.notifyItemChanged(position)
                                var filteredMovieList = state.movieList
                                genresAdapter.currentList.forEach {genre ->
                                    if (genre.isChecked) {
                                        filteredMovieList = filteredMovieList.filter { it.genres.contains(genre.name) }
                                    }
                                }
                                moviesAdapter.submitList(filteredMovieList)
                            }
                        }

                        is WatchListFragmentState.Error -> {
                            binding.recyclerViewWatchList.visibility = View.GONE
                            binding.layoutErrorState.visibility = View.VISIBLE
                            binding.textViewErrorMessage.text =
                                ContextCompat.getString(requireContext(), state.msgResId)
                        }

                        WatchListFragmentState.Loading -> {
                            moviesAdapter.submitList(MediumMovieItemListAdapter.fakeList.take(3))
                        }
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
            findNavController().navigate(
                R.id.action_navigation_watch_list_to_movieInfoFragment,
                MovieInfoFragment.getBundle(movieId)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}