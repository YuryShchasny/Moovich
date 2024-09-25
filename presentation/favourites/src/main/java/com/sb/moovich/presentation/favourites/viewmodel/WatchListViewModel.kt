package com.sb.moovich.presentation.favourites.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.R
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.domain.usecases.watch.GetWatchMoviesUseCase
import com.sb.moovich.presentation.favourites.ui.WatchListFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
@Inject
constructor(
    private val getWatchMoviesUseCase: GetWatchMoviesUseCase,
) : ViewModel() {
    private val _state =
        MutableStateFlow<WatchListFragmentState>(WatchListFragmentState.Loading)
    val state = _state.asStateFlow()

    fun getMovies() {
        launch {
            _state.update {
                val list = getWatchMoviesUseCase()
                if (list.isEmpty()) {
                    WatchListFragmentState.Error(R.string.error_empty_watch_list)
                } else {
                    WatchListFragmentState.Content(list)
                }
            }
        }
    }
}
