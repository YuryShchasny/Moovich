package com.sb.moovich.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.usecases.filter.GetCountriesUseCase
import com.sb.moovich.domain.usecases.filter.GetGenresUseCase
import com.sb.moovich.domain.usecases.filter.GetSearchFilterUseCase
import com.sb.moovich.domain.usecases.filter.SaveSearchFilterUseCase
import com.sb.moovich.presentation.search.model.filter.FilterFragmentEvent
import com.sb.moovich.presentation.search.model.filter.FilterFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    getSearchFilterUseCase: GetSearchFilterUseCase,
    getGenresUseCase: GetGenresUseCase,
    getCountriesUseCase: GetCountriesUseCase,
    private val saveSearchFilterUseCase: SaveSearchFilterUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<FilterFragmentState>(FilterFragmentState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val genres = getGenresUseCase()
            val countries = getCountriesUseCase()
            val filter = getSearchFilterUseCase()
            _state.update { FilterFragmentState.Content(filter, genres, countries) }
        }
    }

    fun fetchEvent(event: FilterFragmentEvent) {
        when (event) {
            FilterFragmentEvent.Reset -> {
                viewModelScope.launch {
                    saveSearchFilterUseCase(Filter())
                }
            }

            is FilterFragmentEvent.SaveFilter -> {
                viewModelScope.launch {
                    saveSearchFilterUseCase(event.filter)
                }
            }

            is FilterFragmentEvent.UpdateFilter -> _state.update {
                when (it) {
                    is FilterFragmentState.Content -> FilterFragmentState.FilterState(event.filter)
                    is FilterFragmentState.FilterState -> FilterFragmentState.FilterState(event.filter)
                    FilterFragmentState.Loading -> throw RuntimeException("Incorrect FilterFragmentState")
                }
            }

            is FilterFragmentEvent.UpdateSliders -> {
                (state.value as? FilterFragmentState.FilterState)?.filter?.let { filter ->
                    fetchEvent(
                        FilterFragmentEvent.UpdateFilter(
                            filter.copy(
                                yearFrom = event.yearFrom,
                                yearTo = event.yearTo,
                                ratingFrom = event.ratingFrom,
                                ratingTo = event.ratingTo
                            )
                        )
                    )
                }
            }
        }
    }
}