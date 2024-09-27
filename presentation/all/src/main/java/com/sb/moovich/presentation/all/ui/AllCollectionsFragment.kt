package com.sb.moovich.presentation.all.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.presentation.all.adapter.AllCollectionsAdapter
import com.sb.moovich.presentation.all.databinding.FragmentAllCollectionsBinding
import com.sb.moovich.presentation.all.ui.model.AllCollectionsFragmentEvent
import com.sb.moovich.presentation.all.ui.model.AllCollectionsFragmentState
import com.sb.moovich.presentation.all.viewmodel.AllCollectionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCollectionsFragment : BaseFragment<FragmentAllCollectionsBinding>() {

    private val viewModel: AllCollectionsViewModel by viewModels()
    private val adapter = AllCollectionsAdapter()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllCollectionsBinding {
        return FragmentAllCollectionsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter.apply {
            onCollectionItemClickListener = {
                viewModel.fetchEvent(AllCollectionsFragmentEvent.OnCollectionClick(it))
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
        collectWithLifecycle(viewModel.state) { state ->
            when (state) {
                AllCollectionsFragmentState.Loading -> {}
                is AllCollectionsFragmentState.Collections -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val scrollState = binding.recyclerView.layoutManager?.onSaveInstanceState()
                    adapter.submit(state.collections, !state.lastPage) {
                        binding.recyclerView.layoutManager?.onRestoreInstanceState(scrollState)
                    }
                }
            }
        }
    }
}