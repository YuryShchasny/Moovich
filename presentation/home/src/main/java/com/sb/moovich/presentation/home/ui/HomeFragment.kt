package com.sb.moovich.presentation.home.ui

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnCancel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sb.moovich.core.adapters.shortmovies.ShortMovieInfo
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.home.R
import com.sb.moovich.presentation.home.databinding.FragmentHomeBinding
import com.sb.moovich.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject lateinit var navigation: INavigation
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = ShortMovieItemListAdapter()
    private val loadAnimator by lazy {
        val animator =
            AnimatorInflater.loadAnimator(context, R.animator.placeholder_movie_card_anim)
        addAnimator(animator)
        animator
    }

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
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { state ->
                    when (state) {
                        is HomeFragmentState.Content -> {
                            stopLoadAnimation()
                            setClickListener()
                            adapter.submitList(state.recommendedList.map {
                                ShortMovieInfo(it.id, it.name, it.rating, it.poster)
                            })
                        }

                        is HomeFragmentState.Error -> {
                        }

                        HomeFragmentState.Loading -> {
                            startLoadAnimation()
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

    private fun startLoadAnimation() {
        adapter.submitList(ShortMovieItemListAdapter.fakeList)
        loadAnimator.apply {
            setTarget(binding.recyclerViewRecommendations)
            // Other views TODO()
            doOnCancel { binding.recyclerViewRecommendations.alpha = 1f }
            start()
        }
    }

    private fun stopLoadAnimation() {
        loadAnimator.cancel()
    }
}
