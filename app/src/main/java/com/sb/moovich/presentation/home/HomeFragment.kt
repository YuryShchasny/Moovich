package com.sb.moovich.presentation.home

import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnCancel
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sb.moovich.R
import com.sb.moovich.databinding.FragmentHomeBinding
import com.sb.moovich.di.MoovichApplication
import com.sb.moovich.di.ViewModelFactory
import com.sb.moovich.presentation.adapters.movies.MovieItemListAdapter
import com.sb.moovich.presentation.home.movie_info.MovieInfoFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MoovichApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private lateinit var adapter: MovieItemListAdapter
    private val loadAnimator by lazy {
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieItemListAdapter(requireContext())
        binding.recyclerViewRecommendations.adapter = adapter
        setObservable()
    }

    private fun setObservable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is HomeFragmentState.Content -> {
                            stopLoadAnimation()
                            setClickListener()
                            adapter.submitList(state.recommendedList)
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
    }

    private fun setClickListener() {
        adapter.onMovieItemClickListener = { movieId ->
            findNavController().navigate(
                R.id.action_navigation_home_to_movieInfoFragment,
                MovieInfoFragment.getBundle(movieId)
            )
        }
    }
    private fun startLoadAnimation() {
        adapter.submitList(MovieItemListAdapter.fakeList)
        loadAnimator.apply {
            setTarget(binding.recyclerViewRecommendations)
            //Other views TODO()
            doOnCancel { binding.recyclerViewRecommendations.alpha = 1f }
            start()
        }
    }

    private fun stopLoadAnimation() {
        loadAnimator.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}