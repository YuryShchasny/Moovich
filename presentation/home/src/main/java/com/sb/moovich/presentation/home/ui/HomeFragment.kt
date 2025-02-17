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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.sb.moovich.core.adapters.shortmovies.ShortMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.extensions.dpToPx
import com.sb.moovich.core.extensions.showMessage
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.presentation.home.adapter.CollectionsAdapter
import com.sb.moovich.presentation.home.adapter.CustomCarouselLayoutManager
import com.sb.moovich.presentation.home.adapter.GenreAdapter
import com.sb.moovich.presentation.home.adapter.MainBoardAdapter
import com.sb.moovich.presentation.home.adapter.MovieCarouselAdapter
import com.sb.moovich.presentation.home.databinding.FragmentHomeBinding
import com.sb.moovich.presentation.home.ui.model.HomeFragmentEvent
import com.sb.moovich.presentation.home.ui.model.HomeFragmentState
import com.sb.moovich.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private val recommendedListAdapter = ShortMovieItemListAdapter()
    private val seriesListAdapter = ShortMovieItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservable()
    }

    override fun onResume() {
        super.onResume()
        updateMainBoardPoints()
    }

    private fun updateMainBoardPoints() {
        val points = binding.pointsLayout.children.toList().filterIsInstance<ImageView>()
        points.forEachIndexed { index, imageView ->
            imageView.updateLayoutParams {
                width =
                    if (index == binding.mainBoardPager.currentItem) 40.dpToPx() else 10.dpToPx()
            }
            imageView.drawable.alpha =
                if (index == binding.mainBoardPager.currentItem) 255 else (255 * 0.4f).toInt()
        }
    }

    private fun initViews() {
        binding.recyclerViewRecommendations.adapter = recommendedListAdapter
        binding.recyclerViewSeries.adapter = seriesListAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.carouselRecyclerView)
    }

    private fun setObservable() {
        collectWithLifecycle(viewModel.state) { state ->
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

                    HomeFragmentState.Loading -> {
                        binding.preloader.visibility = View.VISIBLE
                    }
                }
            }
        }
        collectWithLifecycle(viewModel.error, Lifecycle.State.CREATED) {
            it.showMessage(binding.root)
        }
    }

    private fun setGenres(list: List<String>) {
        binding.recyclerViewGenres.adapter = GenreAdapter(list, onClickListener = {
            viewModel.fetchEvent(HomeFragmentEvent.OnGenreClick(it))
        })
    }

    private fun setTop10Series(list: List<Movie>) {
        seriesListAdapter.submitList(list)
    }

    private fun setCollections(list: List<Collection>) {
        binding.recyclerViewCollections.adapter = CollectionsAdapter(list, onClickListener = {
            viewModel.fetchEvent(HomeFragmentEvent.OnCollectionClick(it))
        })
    }

    private fun setTop10Month(list: List<Movie>) {
        val lm = CustomCarouselLayoutManager(requireContext(), binding.carouselRecyclerView)
        val adapter = MovieCarouselAdapter(list, onClickListener = {
            viewModel.fetchEvent(HomeFragmentEvent.OnMovieClick(it))
        })
        binding.carouselRecyclerView.adapter = adapter
        binding.carouselRecyclerView.layoutManager = lm
        binding.carouselRecyclerView.scrollToPosition(999999)
        binding.carouselRecyclerView.post {
            lm.initiateChildrenUpdate()
        }
    }

    private fun setRecommendedList(list: List<Movie>) {
        recommendedListAdapter.submitList(list)
    }

    private fun setMainBoard(list: List<Movie>) {
        binding.mainBoardPager.adapter = MainBoardAdapter(list, onClickListener = {
            viewModel.fetchEvent(HomeFragmentEvent.OnMovieClick(it))
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
                if (positionOffsetPixels == 0) {
                    updateMainBoardPoints()
                }
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
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun addMainBoardPoints() {
        binding.pointsLayout.removeAllViews()
        repeat(5) {
            binding.pointsLayout.addView(
                ImageView(requireContext()).apply {
                    setImageDrawable(getDrawableCompat(com.sb.moovich.core.R.drawable.point))
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
        recommendedListAdapter.onMovieItemClickListener = { movie ->
            viewModel.fetchEvent(HomeFragmentEvent.OnMovieClick(movie))
        }
        seriesListAdapter.onMovieItemClickListener = { movie ->
            viewModel.fetchEvent(HomeFragmentEvent.OnMovieClick(movie))
        }
        binding.seeAllRecommendations.setOnClickListener { viewModel.fetchEvent(HomeFragmentEvent.SeeAllRecommendations) }
        binding.seeAllSeries.setOnClickListener { viewModel.fetchEvent(HomeFragmentEvent.SeeAllSeries) }
        binding.seeAllCollectionsTextView.setOnClickListener {
            viewModel.fetchEvent(
                HomeFragmentEvent.SeeAllCollections
            )
        }
    }
}
