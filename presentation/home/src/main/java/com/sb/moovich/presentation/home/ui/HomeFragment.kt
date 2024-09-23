package com.sb.moovich.presentation.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.sb.moovich.core.adapters.shortmovies.ShortMovie
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.extensions.dpToPx
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.home.R
import com.sb.moovich.presentation.home.adapter.CollectionsAdapter
import com.sb.moovich.presentation.home.adapter.CustomCarouselLayoutManager
import com.sb.moovich.presentation.home.adapter.GenreAdapter
import com.sb.moovich.presentation.home.adapter.MainBoardAdapter
import com.sb.moovich.presentation.home.adapter.MovieCarouselAdapter
import com.sb.moovich.presentation.home.databinding.FragmentHomeBinding
import com.sb.moovich.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var navigation: INavigation
    private val viewModel: HomeViewModel by viewModels()
    private val recommendedListAdapter = ShortMovieItemListAdapter()
    private val seriesListAdapter = ShortMovieItemListAdapter()

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
        initViews()
        setObservable()
    }

    private fun initViews() {
        binding.recyclerViewRecommendations.adapter = recommendedListAdapter
        binding.recyclerViewSeries.adapter = seriesListAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.carouselRecyclerView)
    }

    private fun setObservable() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    bindingOrNull?.let {
                        when (state) {
                            is HomeFragmentState.Content -> {
                                binding.preloader.visibility = View.GONE
                                binding.main.visibility = View.VISIBLE
                                setClickListener()
                                setMainBoard(state.mainBoardList)
                                setTop10Month(state.top10MonthList)
                                setRecommendedList(state.recommendedList)
                                setCollections(state.collections)
                                setTop10Series(state.top10Series)
                                setGenres(state.genres)
                            }

                            is HomeFragmentState.Error -> {

                            }

                            HomeFragmentState.Loading -> {
                                binding.preloader.visibility = View.VISIBLE
                            }
                        }
                    }
                }
        }
    }

    private fun setGenres(list: List<String>) {
        binding.recyclerViewGenres.adapter = GenreAdapter(list, onClickListener = {
            navigation.navigateToAllMovies(GetAllType.Genre(it))
        })
    }

    private fun setTop10Series(list: List<Movie>) {
        seriesListAdapter.submitList(list.map {
            ShortMovie(it.id, it.name, it.rating, it.poster)
        })

    }

    private fun setCollections(list: List<Collection>) {
        binding.recyclerViewCollections.adapter = CollectionsAdapter(list, onClickListener = {
            navigation.navigateToCollection(it)
        })
    }

    private fun setTop10Month(list: List<Movie>) {
        val lm = CustomCarouselLayoutManager(requireContext(), binding.carouselRecyclerView)
        val adapter = MovieCarouselAdapter(list, onClickListener = {
            navigation.navigateToMovie(it)
        })
        binding.carouselRecyclerView.adapter = adapter
        binding.carouselRecyclerView.layoutManager = lm
        binding.carouselRecyclerView.scrollToPosition(999999)
        binding.carouselRecyclerView.post {
            lm.initiateChildrenUpdate()
        }
    }

    private fun setRecommendedList(list: List<Movie>) {
        recommendedListAdapter.submitList(list.map {
            ShortMovie(it.id, it.name, it.rating, it.poster)
        })
    }

    private fun setMainBoard(list: List<Movie>) {
        binding.mainBoardPager.adapter = MainBoardAdapter(list, onClickListener = {
            navigation.navigateToMovie(it)
        })
        addMainBoardPoints()
        addMainBoardListener()
        binding.mainBoardPager.setCurrentItem(1, false)
        binding.mainBoardPager.setCurrentItem(0, false)
    }

    private fun addMainBoardListener() {
        var scrollJob: Job? = null
        val points = binding.pointsLayout.children.toList().filterIsInstance<ImageView>()
        binding.mainBoardPager.clearOnPageChangeListeners()
        binding.mainBoardPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position + 1 != 5) {
                    points[position + 1].updateLayoutParams {
                        width = (10.dpToPx() + ((40 - 10) * positionOffset).dpToPx()).toInt()
                    }
                    points[position + 1].drawable.alpha =
                        (255 * 0.4 + (255 * 0.6 * positionOffset)).toInt()
                }
                points[position].updateLayoutParams {
                    width = (40.dpToPx() - ((40 - 10) * positionOffset).dpToPx()).toInt()
                }
                points[position].drawable.alpha = (255 - (255 * 0.4 * positionOffset)).toInt()
            }

            override fun onPageSelected(position: Int) {
                scrollJob?.cancel()
                scrollJob = lifecycleScope.launch {
                    delay(5000)
                    bindingOrNull?.mainBoardPager?.setCurrentItem(
                        ((binding.mainBoardPager.currentItem + 1) % 5),
                        true
                    )
                }
                points.forEachIndexed { index, imageView ->
                    imageView.updateLayoutParams {
                        width = if (index == position) 40.dpToPx() else 10.dpToPx()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun addMainBoardPoints() {
        binding.pointsLayout.removeAllViews()
        repeat(5) {
            binding.pointsLayout.addView(
                ImageView(requireContext()).apply {
                    setImageDrawable(getDrawableCompat(R.drawable.point))
                    drawable.alpha = (255 * 0.4).toInt()
                },
                LinearLayout.LayoutParams(if (it == 0) 40.dpToPx() else 10.dpToPx(), 10.dpToPx())
                    .apply {
                        if (it != 4) setMargins(0, 0, 10.dpToPx(), 0)
                    }
            )
        }
    }


    private fun setClickListener() {
        recommendedListAdapter.onMovieItemClickListener = { movieId ->
            navigation.navigateToMovie(movieId)
        }
        seriesListAdapter.onMovieItemClickListener = { movieId ->
            navigation.navigateToMovie(movieId)
        }
        binding.seeAllRecommendations.setOnClickListener { navigation.navigateToAllMovies(GetAllType.Recommendations) }
        binding.seeAllSeries.setOnClickListener { navigation.navigateToAllMovies(GetAllType.Series) }
        binding.seeAllCollectionsTextView.setOnClickListener { navigation.navigateToAllCollections() }
    }
}
