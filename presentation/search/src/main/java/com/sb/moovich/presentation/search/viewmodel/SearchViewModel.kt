package com.sb.moovich.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.usecases.FindMovieUseCase
import com.sb.moovich.domain.usecases.GetRecentMoviesUseCase
import com.sb.moovich.presentation.search.model.search.SearchFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentMoviesUseCase: GetRecentMoviesUseCase,
    private val findMovieUseCase: FindMovieUseCase,
) : ViewModel() {
    companion object {
        const val MAX_SEARCH_COUNT = 30
    }

    private val _state =
        MutableStateFlow<SearchFragmentState>(SearchFragmentState.Loading)

    val state = _state.asStateFlow()

    fun getRecentMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                SearchFragmentState.Content.RecentList(getRecentMoviesUseCase())
            }
        }
    }

    fun findMovie(
        name: String,
        count: Int,
    ) {
        if (name.isNotEmpty()) {
            _state.update { SearchFragmentState.Loading }
            viewModelScope.launch(Dispatchers.IO) {
                val list = findMovieUseCase(name, count)
                _state.update {
                    SearchFragmentState.Content.FindList(
                        list
                            .filter { it.name != "" }
                            .sortedByDescending { it.year },
                        name,
                        count == MAX_SEARCH_COUNT,
                    )
                }
            }
        }
    }
}
