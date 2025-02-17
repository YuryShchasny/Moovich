package com.sb.moovich.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.exceptions.ResponseExceptions
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.repository.MovieRepository
import com.sb.moovich.domain.repository.SearchRepository
import com.sb.moovich.presentation.home.ui.model.HomeFragmentEvent
import com.sb.moovich.presentation.home.ui.model.HomeFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigation: INavigation,
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HomeFragmentState>(HomeFragmentState.Loading)
    val state = _state.asStateFlow()

    private val _error = MutableSharedFlow<ResponseExceptions>()
    val error = _error.asSharedFlow()

    init {
        launch(_error) {
            _state.update() {
                HomeFragmentState.Content(
                    movieRepository.getMainBoardMovies(),
                    movieRepository.getTop10MonthMovies(),
                    movieRepository.getRecommendedMovies(),
                    movieRepository.getCollections(),
                    movieRepository.getTop10Series(),
                    searchRepository.getGenres()
                )
            }
        }
    }

    fun fetchEvent(event: HomeFragmentEvent) {
        when (event) {
            is HomeFragmentEvent.OnCollectionClick -> navigation.navigateToCollection(event.collection)
            is HomeFragmentEvent.OnGenreClick -> navigation.navigateToAllMovies(
                GetAllType.Genre(
                    event.genre
                )
            )

            is HomeFragmentEvent.OnMovieClick -> navigation.navigateToMovie(event.movie.id)
            HomeFragmentEvent.SeeAllCollections -> navigation.navigateToAllCollections()
            HomeFragmentEvent.SeeAllRecommendations -> navigation.navigateToAllMovies(GetAllType.Recommendations)
            HomeFragmentEvent.SeeAllSeries -> navigation.navigateToAllMovies(GetAllType.Series)
        }
    }
}
