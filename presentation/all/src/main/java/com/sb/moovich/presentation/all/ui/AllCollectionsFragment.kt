package com.sb.moovich.presentation.all.ui

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
import com.sb.moovich.core.base.BaseFragment
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.presentation.all.adapter.AllCollectionsAdapter
import com.sb.moovich.presentation.all.databinding.FragmentAllCollectionsBinding
import com.sb.moovich.presentation.all.viewmodel.AllCollectionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllCollectionsFragment : BaseFragment<FragmentAllCollectionsBinding>() {
    @Inject
    lateinit var navigation: INavigation
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
                navigation.navigateToCollection(it)
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
                        AllCollectionsState.Loading -> {}
                        is AllCollectionsState.Collections -> {
                            val scrollState = binding.recyclerView.layoutManager?.onSaveInstanceState()
                            adapter.submitList(state.collections) {
                                binding.recyclerView.layoutManager?.onRestoreInstanceState(scrollState)
                            }
                        }
                    }
                }
        }
    }
}