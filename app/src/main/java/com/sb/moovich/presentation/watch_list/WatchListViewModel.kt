package com.sb.moovich.presentation.watch_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.R
import com.sb.moovich.domain.usecases.GetWatchMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WatchListViewModel @Inject constructor(
    private val getWatchMoviesUseCase: GetWatchMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WatchListFragmentState>(WatchListFragmentState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getWatchMoviesUseCase().collect {
                _state.value = if (it.isEmpty()) {
                    WatchListFragmentState.Error(R.string.error_empty_watch_list)
                } else {
                    WatchListFragmentState.Content(it)
                }
            }
        }
    }

}