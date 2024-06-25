package com.sb.moovich.presentation.home.ui

import android.animation.AnimatorInflater
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnCancel
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.sb.moovich.core.adapters.shortmovies.ShortMovieInfo
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.base.DeepLinkRequestBuilder
import com.sb.moovich.presentation.home.R
import com.sb.moovich.presentation.home.databinding.FragmentHomeBinding
import com.sb.moovich.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
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
            DeepLinkRequestBuilder(
                findNavController(),
                com.sb.moovich.core.R.string.fragment_info_deeplink
            )
                .setNavigateAfterBuild(true)
                .addArguments(com.sb.moovich.core.R.string.fragment_info_argument to movieId)
                .build()
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
