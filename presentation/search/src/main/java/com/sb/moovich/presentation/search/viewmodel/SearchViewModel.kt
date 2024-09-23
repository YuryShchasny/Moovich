package com.sb.moovich.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.usecases.all.MovieNextPageUseCase
import com.sb.moovich.domain.usecases.filter.GetSearchFilterUseCase
import com.sb.moovich.domain.usecases.filter.SaveSearchFilterUseCase
import com.sb.moovich.domain.usecases.find.FindMovieUseCase
import com.sb.moovich.domain.usecases.find.FindMovieWithFilterUseCase
import com.sb.moovich.domain.usecases.recent.GetRecentMoviesUseCase
import com.sb.moovich.presentation.search.model.search.SearchFragmentEvent
import com.sb.moovich.presentation.search.model.search.SearchFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentMoviesUseCase: GetRecentMoviesUseCase,
    private val getFilterUseCase: GetSearchFilterUseCase,
    private val findMovieUseCase: FindMovieUseCase,
    private val findMovieWithFilterUseCase: FindMovieWithFilterUseCase,
    private val saveSearchFilterUseCase: SaveSearchFilterUseCase,
    private val movieNextPageUseCase: MovieNextPageUseCase
) : ViewModel() {
    companion object {
        const val DEFAULT_SEARCH_COUNT = 10
        const val MAX_SEARCH_COUNT = 30
    }

    private var filterJob: Job? = null

    private val _state = MutableStateFlow<SearchFragmentState>(SearchFragmentState.Loading)
    val state = _state.asStateFlow()

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val filter = getFilterUseCase()
            if (filter.hasFilters()) findMoviesWithFilter(filter, DEFAULT_SEARCH_COUNT)
            else fetchEvent(SearchFragmentEvent.RecentMovies)
        }
    }

    fun fetchEvent(event: SearchFragmentEvent) {
        when (event) {
            is SearchFragmentEvent.FindMovie -> findMovie(event.name, event.count)
            SearchFragmentEvent.RecentMovies -> getRecentMovies()
            SearchFragmentEvent.ResetFilters -> resetFilters()
        }
    }

    fun nextPage() {
        if(_state.value is SearchFragmentState.Content.FilterList) {
            viewModelScope.launch(Dispatchers.IO) {
                movieNextPageUseCase()
            }
        }
    }

    private fun resetFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            saveSearchFilterUseCase.invoke(Filter())
        }
        getRecentMovies()
    }

    private fun getRecentMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                SearchFragmentState.Content.RecentList(getRecentMoviesUseCase())
            }
        }
    }

    private fun findMoviesWithFilter(filter: Filter, count: Int) {
        _state.update { SearchFragmentState.Loading }
        filterJob?.cancel()
        filterJob = viewModelScope.launch(Dispatchers.IO) {
            findMovieWithFilterUseCase(filter, count).collect { list ->
                _state.update { state ->
                    if(state is SearchFragmentState.Content.FilterList) {
                        state.copy(
                            findList = state.findList + list.filter { it.name.isNotEmpty() },
                            filtersCount = filter.getFiltersCount()
                        )
                    } else {
                        SearchFragmentState.Content.FilterList(
                            list.filter { it.name.isNotEmpty() },
                            filter.getFiltersCount(),
                        )
                    }
                }
            }
        }
    }

    private fun findMovie(
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
