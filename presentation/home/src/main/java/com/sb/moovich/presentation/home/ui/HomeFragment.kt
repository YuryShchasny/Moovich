package com.sb.moovich.presentation.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sb.moovich.core.adapters.shortmovies.ShortMovie
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.home.databinding.FragmentHomeBinding
import com.sb.moovich.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var navigation: INavigation
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ShortMovieItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewRecommendations.adapter = adapter
        viewModel.getMovies()
        setObservable()
    }

    private fun setObservable() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is HomeFragmentState.Content -> {
                            setClickListener()
                            adapter.submitList(state.recommendedList.map {
                                ShortMovie.ShortMovieInfo(it.id, it.name, it.rating, it.poster)
                            })
                        }

                        is HomeFragmentState.Error -> {
                        }

                        HomeFragmentState.Loading -> {
                            adapter.submitList((0..10).map { ShortMovie.Shimmer })
                        }
                    }
                }
        }
    }

    private fun setClickListener() {
        adapter.onMovieItemClickListener = { movieId ->
            navigation.navigateToMovie(movieId)
        }
    }
}
