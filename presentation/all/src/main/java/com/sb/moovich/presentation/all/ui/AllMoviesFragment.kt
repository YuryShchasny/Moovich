package com.sb.moovich.presentation.all.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.presentation.all.databinding.FragmentAllMoviesBinding
import com.sb.moovich.presentation.all.viewmodel.AllMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllMoviesFragment : BaseFragment<FragmentAllMoviesBinding>() {
    @Inject
    lateinit var navigation: INavigation
    private val viewModel: AllMoviesViewModel by viewModels()
    private val adapter = MediumMovieItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllMoviesBinding {
        return FragmentAllMoviesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("type", GetAllType::class.java)!!
            } else {
                it.getSerializable("type") as GetAllType
            }
            viewModel.initMovies(type)
        }
        binding.recyclerView.adapter = adapter.apply {
            onMovieItemClickListener = {
                navigation.navigateToMovie(it)
            }
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (recyclerView.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                        viewModel.nextPage()
                    }
                }
            }
        })
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        AllMoviesState.Loading -> {}
                        is AllMoviesState.Movies -> {
                            val scrollState = binding.recyclerView.layoutManager?.onSaveInstanceState()
                            adapter.submitList(state.movieList) {
                                binding.recyclerView.layoutManager?.onRestoreInstanceState(scrollState)
                            }
                        }
                    }
                }
        }
    }
}