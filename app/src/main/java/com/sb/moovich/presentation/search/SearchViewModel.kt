package com.sb.moovich.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.usecases.FindMovieUseCase
import com.sb.moovich.domain.usecases.GetRecentMoviesUseCase
import com.sb.moovich.presentation.utils.mergeWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    getRecentMoviesUseCase: GetRecentMoviesUseCase,
    private val findMovieUseCase: FindMovieUseCase
) : ViewModel() {
    private val _state =
        MutableStateFlow<SearchFragmentState>(SearchFragmentState.Loading)
    val state = _state.mergeWith(
        getRecentMoviesUseCase().map { SearchFragmentState.Content.RecentList(it) }
    )

    fun findMovie(name: String, count: Int) {
        if (name.isNotEmpty()) {
            _state.update { SearchFragmentState.Loading }
            viewModelScope.launch {
                findMovieUseCase(name, count).collect { list ->
                    _state.update {
                        SearchFragmentState.Content.FindList(
                            list.filter { it.name != "" }
                                .sortedByDescending { it.year },
                            name
                        )
                    }
                }
            }
        }
    }

}