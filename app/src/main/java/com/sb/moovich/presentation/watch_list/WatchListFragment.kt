package com.sb.moovich.presentation.watch_list

import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnCancel
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sb.moovich.R
import com.sb.moovich.databinding.FragmentWatchListBinding
import com.sb.moovich.di.MoovichApplication
import com.sb.moovich.di.ViewModelFactory
import com.sb.moovich.presentation.adapters.movies.MovieItemListAdapter
import com.sb.moovich.presentation.home.movie_info.MovieInfoFragment
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

    private lateinit var adapter: MovieItemListAdapter
    private val animator by lazy {
        AnimatorInflater.loadAnimator(context, R.animator.placeholder_movie_card_anim)
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
        initRecyclerView()
        observeWatchList()
    }


    private fun observeWatchList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {state ->
                    when(state) {
                        is WatchListFragmentState.Content -> {
                            animator.cancel()
                            setClickListener()
                            adapter.submitList(state.movieList)
                        }
                        is WatchListFragmentState.Error -> {
                            binding.recyclerViewWatchList.visibility = View.GONE
                            binding.layoutErrorState.visibility = View.VISIBLE
                            binding.textViewErrorMessage.text = ContextCompat.getString(requireContext(), state.msgResId)
                        }
                        WatchListFragmentState.Loading -> {
                            adapter.submitList(MovieItemListAdapter.fakeList.take(3))
                            animator.apply {
                                setTarget(binding.recyclerViewWatchList)
                                doOnCancel { binding.recyclerViewWatchList.alpha = 1f }
                                start()
                            }
                        }
                    }
                }
            }
        }
    }
    private fun initRecyclerView() {
        val screenWidth = resources.displayMetrics.widthPixels
        val itemWidth = resources.getDimensionPixelSize(R.dimen.item_movie_card_width)
        val marginWidth = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        val marginEnd = resources.getDimensionPixelSize(R.dimen.item_movie_card_margin)
        val spanCount = (screenWidth - marginWidth * 2 - marginEnd) / itemWidth
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.recyclerViewWatchList.layoutManager = layoutManager
        adapter = MovieItemListAdapter(requireContext())
        binding.recyclerViewWatchList.adapter = adapter
    }

    private fun setClickListener() {
        adapter.onMovieItemClickListener = { movieId ->
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