package com.sb.moovich.presentation.collection.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.adapters.mediummovies.MediumMovieItemListAdapter
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.extensions.loadCoil
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.presentation.collection.databinding.FragmentCollectionBinding
import com.sb.moovich.presentation.collection.ui.model.CollectionFragmentEvent
import com.sb.moovich.presentation.collection.ui.model.CollectionFragmentState
import com.sb.moovich.presentation.collection.viewmodel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionFragment : BaseFragment<FragmentCollectionBinding>() {

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
            setCollection(collection)
            viewModel.init(collection.slug)
        }
        setupRecyclerView()
        setObservable()
    }

    private fun setCollection(collection: Collection) {
        binding.textViewName.text = collection.name
        binding.textViewCount.text =
            getString(com.sb.moovich.core.R.string.count, collection.count)
        binding.imageViewBackdrop.loadCoil(collection.cover)
    }

    private fun setObservable() {
        collectWithLifecycle(viewModel.state, Lifecycle.State.CREATED) { state ->
            when (state) {
                CollectionFragmentState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is CollectionFragmentState.Movies -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val scrollState =
                        binding.recyclerView.layoutManager?.onSaveInstanceState()
                    adapter.submit(state.movieList, !state.lastPage) {
                        binding.recyclerView.layoutManager?.onRestoreInstanceState(
                            scrollState
                        )
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter.apply {
            onMovieItemClickListener = {
                viewModel.fetchEvent(CollectionFragmentEvent.OnMovieClick(it))
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
