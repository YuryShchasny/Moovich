package com.sb.moovich.presentation.favourites.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.repository.WatchMovieRepository
import com.sb.moovich.presentation.favourites.adapter.Genre
import com.sb.moovich.presentation.favourites.ui.model.WatchListFragmentEvent
import com.sb.moovich.presentation.favourites.ui.model.WatchListFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
@Inject
constructor(
    private val navigation: INavigation,
    private val watchMovieRepository: WatchMovieRepository
) : ViewModel() {
    private val _state =
        MutableStateFlow<WatchListFragmentState>(WatchListFragmentState.Loading)
    val state = _state.asStateFlow()

    fun init() {
        launch {
            _state.update { state ->
                val list = watchMovieRepository.getWatchMovies()
                if (list.isEmpty()) {
                    WatchListFragmentState.EmptyContent
                } else {
                    val genresList = mutableSetOf<String>()
                    list.forEach {
                        genresList.addAll(it.genres)
                    }
                    if (state is WatchListFragmentState.Content) {
                        val checkedGenres = state.genres.filter { it.isChecked }.map { it.name }
                        state.copy(
                            movieList = list,
                            genres = genresList.sorted()
                                .map { Genre(it, checkedGenres.contains(it)) }
                        )
                    } else {
                        WatchListFragmentState.Content(
                            movieList = list,
                            genres = genresList.sorted().map { Genre(it, false) })
                    }
                }
            }
        }
    }

    fun fetchEvent(event: WatchListFragmentEvent) {
        when (event) {
            is WatchListFragmentEvent.OnGenreClick -> {
                _state.update { state ->
                    (state as? WatchListFragmentState.Content)?.let {
                        state.copy(genres = it.genres.map { genre ->
                            if (event.genre == genre) genre.copy(isChecked = !genre.isChecked)
                            else genre
                        })
                    } ?: state
                }
            }

            is WatchListFragmentEvent.OnMovieClick -> navigation.navigateToMovie(event.movie.id)
        }
    }
}
