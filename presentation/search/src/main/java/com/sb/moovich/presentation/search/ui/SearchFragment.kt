package com.sb.moovich.presentation.search.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sb.moovich.core.R
import com.sb.moovich.core.adapters.mediummovies.MediumMovieInfo
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.base.DeepLinkRequestBuilder
import com.sb.moovich.presentation.search.databinding.FragmentSearchBinding
import com.sb.moovich.presentation.search.model.search.SearchFragmentState
import com.sb.moovich.presentation.search.viewmodel.SearchViewModel
import com.sb.moovich.presentation.search.viewmodel.SearchViewModel.Companion.MAX_SEARCH_COUNT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()

    private val adapter = MediumMovieItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewRecentAndResults.adapter = adapter
        viewModel.getRecentMovies()
        setObservable()
        setSearchListener()
    }

    private fun setSearchListener() {
        binding.searchEditText.setOnEditorActionListener { v, _, _ ->
            viewModel.findMovie(v.text.toString(), 10)
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObservable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is SearchFragmentState.Content -> {
                            binding.recyclerViewRecentAndResults.visibility = View.VISIBLE
                            binding.progressBarSearch.visibility = View.GONE
                            when (state) {
                                is SearchFragmentState.Content.FindList -> {
                                    binding.textViewTitle.text =
                                        ContextCompat.getString(requireContext(), R.string.results)
                                    binding.textViewSearchSeeAll.text =
                                        ContextCompat.getString(requireContext(), R.string.see_all)
                                    adapter.submitList(state.findList.map {
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

                                is SearchFragmentState.Content.RecentList -> {
                                    binding.textViewTitle.text =
                                        ContextCompat.getString(requireContext(), R.string.recent)
                                    binding.textViewSearchSeeAll.text =
                                        ContextCompat.getString(
                                            requireContext(),
                                            R.string.see_all_history,
                                        )
                                    adapter.submitList(state.recentList.take(10).map {
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
                            setClickListeners(state)
                        }

                        is SearchFragmentState.Error -> {
                        }

                        SearchFragmentState.Loading -> {
                            binding.recyclerViewRecentAndResults.visibility = View.GONE
                            binding.progressBarSearch.visibility = View.VISIBLE
                        }

                        SearchFragmentState.Filters -> TODO()
                    }
                }
            }
        }
    }

    private fun setClickListeners(state: SearchFragmentState.Content) {
        binding.filterButton.setOnClickListener {
            DeepLinkRequestBuilder(
                findNavController(),
                R.string.fragment_filter_deeplink
            )
                .setNavigateAfterBuild(true)
                .build()
        }
        adapter.onMovieItemClickListener = { movieId ->
            DeepLinkRequestBuilder(
                findNavController(),
                R.string.fragment_info_deeplink
            )
                .setNavigateAfterBuild(true)
                .addArguments(R.string.fragment_info_argument to movieId)
                .build()
        }
        binding.textViewSearchSeeAll.setOnClickListener {
            state.seeAll = !state.seeAll
            if (state.seeAll) {
                binding.textViewSearchSeeAll.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary,
                    ),
                )
                if (state is SearchFragmentState.Content.RecentList) {
                    adapter.submitList(state.recentList.map {
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
                } else if (state is SearchFragmentState.Content.FindList) {
                    viewModel.findMovie(state.searchName, MAX_SEARCH_COUNT)
                }
            } else {
                binding.textViewSearchSeeAll.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white,
                    ),
                )
                if (state is SearchFragmentState.Content.RecentList) {
                    adapter.submitList(state.recentList.take(10).map {
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
                } else if (state is SearchFragmentState.Content.FindList) {
                    viewModel.findMovie(state.searchName, 10)
                }
            }
            binding.recyclerViewRecentAndResults.scrollToPosition(0)
        }
    }
}
