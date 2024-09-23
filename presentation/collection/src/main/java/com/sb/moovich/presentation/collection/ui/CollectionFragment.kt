package com.sb.moovich.presentation.collection.ui

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
import coil.load
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.presentation.collection.databinding.FragmentCollectionBinding
import com.sb.moovich.presentation.collection.viewmodel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CollectionFragment : BaseFragment<FragmentCollectionBinding>() {

    @Inject
    lateinit var navigation: INavigation
    private val viewModel: CollectionViewModel by viewModels()
    private val adapter = MediumMovieItemListAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCollectionBinding {
        return FragmentCollectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("collection", Collection::class.java)!!
            } else {
                it.getSerializable("collection") as Collection
            }
            binding.textViewName.text = collection.name
            binding.textViewCount.text =
                getString(com.sb.moovich.core.R.string.count, collection.count)
            binding.imageViewBackdrop.load(collection.cover)
            viewModel.init(collection.slug)
        }
        setupRecyclerView()
        setObservable()
    }

    private fun setObservable() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect { state ->
                    when (state) {
                        CollectionState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is CollectionState.Movies -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            val scrollState = binding.recyclerView.layoutManager?.onSaveInstanceState()
                            adapter.submitList(state.movieList) {
                                binding.recyclerView.layoutManager?.onRestoreInstanceState(scrollState)
                            }
                        }
                    }
                }
        }
    }

    private fun setupRecyclerView() {
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
    }
}