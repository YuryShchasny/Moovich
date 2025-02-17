package com.sb.moovich.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.repository.SearchRepository
import com.sb.moovich.presentation.search.ui.model.filter.FilterFragmentEvent
import com.sb.moovich.presentation.search.ui.model.filter.FilterFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val navigation: INavigation,
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<FilterFragmentState>(FilterFragmentState.Loading)
    val state = _state.asStateFlow()

    init {
        launch {
            val genres = searchRepository.getGenres()
            val countries = searchRepository.getCountries()
            val filter = searchRepository.getFilter()
            _state.update { FilterFragmentState.Content(filter, genres, countries) }
        }
    }

    fun fetchEvent(event: FilterFragmentEvent) {
        when (event) {
            FilterFragmentEvent.Reset -> {
                launch {
                    searchRepository.saveFilter(Filter())
                }
                navigation.navigateUp()
            }

            is FilterFragmentEvent.SaveFilter -> {
                launch {
                    val state = _state.value as FilterFragmentState.Content
                    searchRepository.saveFilter(state.filter)
                }
                navigation.navigateUp()
            }

            is FilterFragmentEvent.UpdateSliders -> {
                _state.update { state ->
                    (state as? FilterFragmentState.Content)?.let {
                        state.copy(
                            filter = state.filter.copy(
                                yearFrom = event.yearFrom,
                                yearTo = event.yearTo,
                                ratingFrom = event.ratingFrom,
                                ratingTo = event.ratingTo
                            )
                        )
                    } ?: state
                }
            }

            FilterFragmentEvent.OnBackPressed -> navigation.navigateUp()
            is FilterFragmentEvent.OnGenreClick -> {
                _state.update { state ->
                    (state as? FilterFragmentState.Content)?.let {
                        state.copy(
                            filter = state.filter.copy(
                                genres =
                                state.filter.genres.toMutableList().apply {
                                    if (contains(event.genre)) remove(event.genre)
                                    else add(event.genre)
                                }
                            )
                        )
                    } ?: state
                }
            }

            is FilterFragmentEvent.OnCountryClick -> {
                _state.update { state ->
                    (state as? FilterFragmentState.Content)?.let {
                        state.copy(
                            filter = state.filter.copy(
                                countries =
                                state.filter.countries.toMutableList().apply {
                                    if (contains(event.country)) remove(event.country)
                                    else add(event.country)
                                }
                            )
                        )
                    } ?: state
                }
            }

            is FilterFragmentEvent.OnTabClick -> {
                _state.update { state ->
                    (state as? FilterFragmentState.Content)?.let {
                        state.copy(
                            filter = state.filter.copy(
                                type = event.type
                            )
                        )
                    } ?: state
                }
            }

            is FilterFragmentEvent.OnSortViewClick -> {
                _state.update { state ->
                    (state as? FilterFragmentState.Content)?.let {
                        state.copy(
                            filter = state.filter.copy(
                                sortType = event.sortType
                            )
                        )
                    } ?: state
                }
            }
        }
    }
}
