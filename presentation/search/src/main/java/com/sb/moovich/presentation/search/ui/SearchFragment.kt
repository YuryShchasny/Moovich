package com.sb.moovich.presentation.search.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.R
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.search.databinding.FragmentSearchBinding
import com.sb.moovich.presentation.search.model.search.SearchFragmentEvent
import com.sb.moovich.presentation.search.model.search.SearchFragmentState
import com.sb.moovich.presentation.search.viewmodel.SearchViewModel
import com.sb.moovich.presentation.search.viewmodel.SearchViewModel.Companion.DEFAULT_SEARCH_COUNT
import com.sb.moovich.presentation.search.viewmodel.SearchViewModel.Companion.MAX_SEARCH_COUNT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    @Inject
    lateinit var navigation: INavigation
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
        binding.recyclerViewRecentAndResults.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    (recyclerView.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                            viewModel.nextPage()
                        }
                    }
                }
            }
        )
        viewModel.init()
        setObservable()
        setSearchListener()
    }

    private fun setSearchListener() {
        binding.searchEditText.setOnEditorActionListener { v, _, _ ->
            viewModel.fetchEvent(
                SearchFragmentEvent.FindMovie(
                    v.text.toString(),
                    DEFAULT_SEARCH_COUNT
                )
            )
            true
        }
    }

    private fun setObservable() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    bindingOrNull?.let { binding ->
                        when (state) {
                            is SearchFragmentState.Content -> {
                                binding.recyclerViewRecentAndResults.visibility = View.VISIBLE
                                binding.progressBarSearch.visibility = View.GONE
                                binding.filterCountLayout.visibility =
                                    if (state is SearchFragmentState.Content.FilterList) View.VISIBLE else View.GONE
                                binding.textViewSearchSeeAll.visibility =
                                    if (state is SearchFragmentState.Content.FilterList) View.GONE else View.VISIBLE
                                binding.layoutErrorState.visibility = View.GONE
                                when (state) {
                                    is SearchFragmentState.Content.FindList -> {
                                        if (state.findList.isEmpty()) binding.layoutErrorState.visibility =
                                            View.VISIBLE
                                        binding.textViewTitle.text =
                                            getStringCompat(R.string.results)
                                        binding.textViewSearchSeeAll.text =
                                            getStringCompat(R.string.see_all)
                                        adapter.submitList(state.findList)
                                    }

                                    is SearchFragmentState.Content.RecentList -> {
                                        binding.textViewTitle.text =
                                            getStringCompat(R.string.recent)
                                        binding.textViewSearchSeeAll.text =
                                            getStringCompat(R.string.see_all_history)
                                        adapter.submitList(state.recentList.take(10))
                                    }

                                    is SearchFragmentState.Content.FilterList -> {
                                        binding.filterCountText.text =
                                            buildString {
                                                append(getStringCompat(R.string.filter))
                                                append(" (${state.filtersCount})")
                                            }
                                        binding.textViewTitle.text =
                                            getStringCompat(R.string.results)
                                        adapter.submitList(state.findList)
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
                        }
                    }
                }
        }
    }

    private fun setClickListeners(state: SearchFragmentState.Content) {
        binding.filterButton.setOnClickListener {
            navigation.navigateToFilter()
        }
        adapter.onMovieItemClickListener = { movieId ->
            navigation.navigateToMovie(movieId)
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
                    val scrollState = binding.recyclerViewRecentAndResults.layoutManager?.onSaveInstanceState()
                    adapter.submitList(state.recentList) {
                        binding.recyclerViewRecentAndResults.layoutManager?.onRestoreInstanceState(
                            scrollState
                        )
                    }
                } else if (state is SearchFragmentState.Content.FindList) {
                    viewModel.fetchEvent(
                        SearchFragmentEvent.FindMovie(
                            state.searchName,
                            MAX_SEARCH_COUNT
                        )
                    )
                }
            } else {
                binding.textViewSearchSeeAll.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white,
                    ),
                )
                if (state is SearchFragmentState.Content.RecentList) {
                    val scrollState = binding.recyclerViewRecentAndResults.layoutManager?.onSaveInstanceState()
                    adapter.submitList(state.recentList.take(10)) {
                        binding.recyclerViewRecentAndResults.layoutManager?.onRestoreInstanceState(
                            scrollState
                        )
                    }
                } else if (state is SearchFragmentState.Content.FindList) {
                    viewModel.fetchEvent(
                        SearchFragmentEvent.FindMovie(
                            state.searchName,
                            DEFAULT_SEARCH_COUNT
                        )
                    )
                }
            }
        }
        binding.filterResetIcon.setOnClickListener {
            viewModel.fetchEvent(SearchFragmentEvent.ResetFilters)
        }
    }
}
