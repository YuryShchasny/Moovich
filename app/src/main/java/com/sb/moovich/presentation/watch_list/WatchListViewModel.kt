package com.sb.moovich.presentation.watch_list

import androidx.lifecycle.ViewModel
import com.sb.moovich.R
import com.sb.moovich.domain.usecases.GetWatchMoviesUseCase
import com.sb.moovich.presentation.utils.mergeWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchListViewModel @Inject constructor(
    getWatchMoviesUseCase: GetWatchMoviesUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<WatchListFragmentState>(WatchListFragmentState.Loading)
    val state = _state.mergeWith(
        getWatchMoviesUseCase().map {
            if (it.isEmpty()) {
                WatchListFragmentState.Error(R.string.error_empty_watch_list)
            }
            else {
                WatchListFragmentState.Content(it)
            }
        }
    )

}