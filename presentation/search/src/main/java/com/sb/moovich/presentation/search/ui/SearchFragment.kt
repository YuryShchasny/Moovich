package com.sb.moovich.presentation.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.R
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.presentation.search.databinding.FragmentSearchBinding
import com.sb.moovich.presentation.search.ui.model.search.SearchFragmentEvent
import com.sb.moovich.presentation.search.ui.model.search.SearchFragmentState
import com.sb.moovich.presentation.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.init()
        setAdapter()
        setObservable()
        setSearchListener()
        setClickListeners()
    }

    private fun setAdapter() {
        binding.recyclerViewRecentAndResults.adapter = adapter
        adapter.onMovieItemClickListener = { movie ->
            viewModel.fetchEvent(SearchFragmentEvent.OnMovieClick(movie))
        }
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
    }

    private fun setSearchListener() {
        binding.searchEditText.setOnEditorActionListener { v, _, _ ->
            viewModel.fetchEvent(
                SearchFragmentEvent.FindMovie(
                    v.text.toString(),
                )
            )
            true
        }
    }

    private fun setObservable() {
        collectWithLifecycle(viewModel.state) { state ->
            bindingOrNull?.let { binding ->
                when (state) {
                    is SearchFragmentState.Content -> {
                        binding.linearLayoutTopBar.visibility = View.VISIBLE
                        binding.recyclerViewRecentAndResults.visibility = View.VISIBLE
                        binding.progressBarSearch.visibility = View.GONE
                        binding.filterCountLayout.visibility =
                            if (state is SearchFragmentState.Content.FilterList) View.VISIBLE else View.GONE
                        binding.textViewSearchSeeAll.visibility =
                            if (state is SearchFragmentState.Content.FilterList) View.GONE else View.VISIBLE
                        binding.layoutNotFound.visibility = View.GONE
                        when (state) {
                            is SearchFragmentState.Content.FindList -> {
                                if (state.findList.isEmpty()) binding.layoutNotFound.visibility =
                                    View.VISIBLE
                                binding.textViewTitle.text =
                                    getStringCompat(R.string.results)
                                binding.textViewSearchSeeAll.text =
                                    getStringCompat(R.string.see_all)
                                adapter.submit(state.findList) {
                                    binding.recyclerViewRecentAndResults.scrollToPosition(0)
                                }
                            }

                            is SearchFragmentState.Content.RecentList -> {
                                binding.textViewTitle.text =
                                    getStringCompat(R.string.recent)
                                binding.textViewSearchSeeAll.text =
                                    getStringCompat(R.string.see_all_history)
                                val list = if (state.seeAllRecentList) state.recentList
                                else state.recentList.take(10)
                                val scrollState =
                                    binding.recyclerViewRecentAndResults.layoutManager?.onSaveInstanceState()
                                adapter.submit(list) {
                                    binding.recyclerViewRecentAndResults.layoutManager?.onRestoreInstanceState(
                                        scrollState
                                    )
                                }
                            }

                            is SearchFragmentState.Content.FilterList -> {
                                binding.filterCountText.text =
                                    buildString {
                                        append(getStringCompat(R.string.filter))
                                        append(" (${state.filtersCount})")
                                    }
                                binding.textViewTitle.text =
                                    getStringCompat(R.string.results)
                                adapter.submit(state.findList, !state.lastPage)
                            }
                        }
                        setSeeAll(state)
                    }

                    SearchFragmentState.Loading -> {
                        binding.recyclerViewRecentAndResults.visibility = View.GONE
                        binding.linearLayoutTopBar.visibility = View.GONE
                        binding.progressBarSearch.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.filterButton.setOnClickListener { viewModel.fetchEvent(SearchFragmentEvent.OnFilterClick) }
        binding.filterResetIcon.setOnClickListener {
            viewModel.fetchEvent(SearchFragmentEvent.ResetFilters)
        }
        binding.textViewSearchSeeAll.setOnClickListener {
            viewModel.fetchEvent(SearchFragmentEvent.OnSeeAllClick)
        }
    }

    private fun setSeeAll(state: SearchFragmentState.Content) {
        val color = if (state.seeAll) R.color.primary else R.color.white
        binding.textViewSearchSeeAll.setTextColor(
            getColorCompat(color)
        )
    }
}
