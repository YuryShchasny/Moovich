package com.sb.moovich.presentation.all.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.repository.MovieRepository
import com.sb.moovich.presentation.all.ui.model.AllCollectionsFragmentEvent
import com.sb.moovich.presentation.all.ui.model.AllCollectionsFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AllCollectionsViewModel @Inject constructor(
    private val navigation: INavigation,
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _state =
        MutableStateFlow<AllCollectionsFragmentState>(AllCollectionsFragmentState.Loading)
    val state = _state.asStateFlow()

    init {
        launch {
            movieRepository.getAllCollections().collect { list ->
                _state.update {
                    val collections =
                        if (it is AllCollectionsFragmentState.Collections) it.collections + list
                        else list
                    AllCollectionsFragmentState.Collections(
                        collections = collections,
                        lastPage = list.isEmpty()
                    )
                }
            }
        }
    }

    fun fetchEvent(event: AllCollectionsFragmentEvent) {
        when (event) {
            is AllCollectionsFragmentEvent.OnCollectionClick -> navigation.navigateToCollection(
                event.collection
            )
        }
    }

    fun nextPage() {
        launch {
            movieRepository.collectionNextPage()
        }
    }
}
