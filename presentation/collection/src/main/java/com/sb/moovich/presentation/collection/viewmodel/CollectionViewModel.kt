package com.sb.moovich.presentation.collection.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.repository.MovieRepository
import com.sb.moovich.presentation.collection.ui.model.CollectionFragmentEvent
import com.sb.moovich.presentation.collection.ui.model.CollectionFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val navigation: INavigation,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow<CollectionFragmentState>(CollectionFragmentState.Loading)
    val state = _state.asStateFlow()

    fun init(slug: String) {
        launch {
            movieRepository.getAllMovies(GetAllType.Collection(slug)).collect { list ->
                _state.update {
                    val movies =
                        if (it is CollectionFragmentState.Movies) it.movieList + list else list
                    CollectionFragmentState.Movies(
                        movieList = movies,
                        lastPage = list.isEmpty()
                    )
                }
            }
        }
    }

    fun fetchEvent(event: CollectionFragmentEvent) {
        when (event) {
            is CollectionFragmentEvent.OnMovieClick -> navigation.navigateToMovie(event.movie.id)
        }
    }

    fun nextPage() {
        launch {
            movieRepository.movieNextPage()
        }
    }
}