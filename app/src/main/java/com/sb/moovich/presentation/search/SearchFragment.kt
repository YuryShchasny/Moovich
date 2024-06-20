package com.sb.moovich.presentation.search

import android.annotation.SuppressLint
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
import com.sb.moovich.databinding.FragmentSearchBinding
import com.sb.moovich.di.MoovichApplication
import com.sb.moovich.di.ViewModelFactory
import com.sb.moovich.presentation.adapters.movies.medium.MediumMovieItemListAdapter
import com.sb.moovich.presentation.movie_info.MovieInfoFragment
import com.sb.moovich.presentation.search.SearchViewModel.Companion.MAX_SEARCH_COUNT
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MoovichApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
    }

    private val adapter by lazy {
        MediumMovieItemListAdapter(requireContext())
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewRecentAndResults.adapter = adapter
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
                viewModel.state.collect {state ->
                    when(state) {
                        is SearchFragmentState.Content -> {
                            binding.recyclerViewRecentAndResults.visibility = View.VISIBLE
                            binding.progressBarSearch.visibility = View.GONE
                            when(state) {
                                is SearchFragmentState.Content.FindList -> {
                                    binding.textViewTitle.text = ContextCompat.getString(requireContext(), R.string.results)
                                    binding.textViewSearchSeeAll.text = ContextCompat.getString(requireContext(), R.string.see_all)
                                    adapter.submitList(state.findList)
                                }
                                is SearchFragmentState.Content.RecentList -> {
                                    binding.textViewTitle.text = ContextCompat.getString(requireContext(), R.string.recent)
                                    binding.textViewSearchSeeAll.text = ContextCompat.getString(requireContext(), R.string.see_all_history)
                                    adapter.submitList(state.recentList.take(10))
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
        adapter.onMovieItemClickListener = { movieId ->
            findNavController().navigate(
                R.id.action_navigation_search_to_movieInfoFragment,
                MovieInfoFragment.getBundle(movieId)
            )
        }
        binding.textViewSearchSeeAll.setOnClickListener {
            state.seeAll = !state.seeAll
            if (state.seeAll) {
                binding.textViewSearchSeeAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
                if(state is SearchFragmentState.Content.RecentList) {
                    adapter.submitList(state.recentList)
                }
                else if(state is SearchFragmentState.Content.FindList) {
                    viewModel.findMovie(state.searchName, MAX_SEARCH_COUNT)
                }
            }
            else {
                binding.textViewSearchSeeAll.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                if(state is SearchFragmentState.Content.RecentList) {
                    adapter.submitList(state.recentList.take(10))
                }
                else if(state is SearchFragmentState.Content.FindList) {
                    viewModel.findMovie(state.searchName, 10)
                }
            }
            binding.recyclerViewRecentAndResults.scrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}