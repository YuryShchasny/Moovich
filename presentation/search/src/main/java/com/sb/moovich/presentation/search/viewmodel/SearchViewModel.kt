package com.sb.moovich.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.repository.MovieRepository
import com.sb.moovich.domain.repository.SearchRepository
import com.sb.moovich.presentation.search.ui.model.search.SearchFragmentEvent
import com.sb.moovich.presentation.search.ui.model.search.SearchFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val navigation: INavigation,
    private val searchRepository: SearchRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    companion object {
        const val DEFAULT_SEARCH_COUNT = 10
        const val MAX_SEARCH_COUNT = 30
    }

    private var filterJob: Job? = null

    private val _state = MutableStateFlow<SearchFragmentState>(SearchFragmentState.Loading)
    val state = _state.asStateFlow()

    fun init() {
        launch {
            val filter = searchRepository.getFilter()
            if (filter.hasFilters()) findMoviesWithFilter(filter)
            else fetchEvent(SearchFragmentEvent.RecentMovies)
        }
    }

    fun fetchEvent(event: SearchFragmentEvent) {
        when (event) {
            is SearchFragmentEvent.FindMovie -> findMovie(
                event.name,
                (_state.value as? SearchFragmentState.Content)?.seeAll ?: false
            )

            SearchFragmentEvent.RecentMovies -> getRecentMovies()
            SearchFragmentEvent.ResetFilters -> resetFilters()
            SearchFragmentEvent.OnFilterClick -> navigation.navigateToFilter()
            is SearchFragmentEvent.OnMovieClick -> navigation.navigateToMovie(event.movie.id)
            SearchFragmentEvent.OnSeeAllClick -> changeSeeAllState()
        }
    }

    fun nextPage() {
        if (_state.value is SearchFragmentState.Content.FilterList) {
            launch {
                movieRepository.movieNextPage()
            }
        }
    }

    private fun changeSeeAllState() {
        _state.update {
            when (it) {
                is SearchFragmentState.Content.FilterList -> it
                is SearchFragmentState.Content.FindList -> {
                    findMovie(it.searchName, !it.seeAllFindList)
                    it.copy(seeAllFindList = !it.seeAllFindList)
                }

                is SearchFragmentState.Content.RecentList -> it.copy(seeAllRecentList = !it.seeAllRecentList)
                SearchFragmentState.Loading -> it
            }
        }
    }

    private fun resetFilters() {
        launch {
            searchRepository.saveFilter(Filter())
        }
        getRecentMovies()
    }

    private fun getRecentMovies() {
        launch {
            _state.update {
                SearchFragmentState.Content.RecentList(searchRepository.getRecentMovies())
            }
        }
    }

    private fun findMoviesWithFilter(filter: Filter) {
        _state.update { SearchFragmentState.Loading }
        filterJob?.cancel()
        filterJob = launch {
            searchRepository.findMoviesWithFilter(filter).collect { list ->
                _state.update { state ->
                    val newList =
                        if (state is SearchFragmentState.Content.FilterList)
                            state.findList + list.filter { it.name.isNotEmpty() }
                        else list.filter { it.name.isNotEmpty() }
                    SearchFragmentState.Content.FilterList(
                        findList = newList,
                        filtersCount = filter.getFiltersCount(),
                        lastPage = list.isEmpty()
                    )
                }
            }
        }
    }

    private fun findMovie(
        name: String,
        seeAll: Boolean,
    ) {
        if (name.isNotEmpty()) {
            val count = if (seeAll) MAX_SEARCH_COUNT else DEFAULT_SEARCH_COUNT
            _state.update { SearchFragmentState.Loading }
            launch {
                val list = searchRepository.findMovie(name, count)
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
        } else if (_state.value !is SearchFragmentState.Content.RecentList) {
            getRecentMovies()
        }
    }
}
