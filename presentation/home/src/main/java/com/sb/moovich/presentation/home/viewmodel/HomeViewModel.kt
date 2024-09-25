package com.sb.moovich.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.domain.usecases.filter.GetGenresUseCase
import com.sb.moovich.domain.usecases.home.GetCollectionsUseCase
import com.sb.moovich.domain.usecases.home.GetMainBoardMoviesUseCase
import com.sb.moovich.domain.usecases.home.GetRecommendedMoviesUseCase
import com.sb.moovich.domain.usecases.home.GetTop10MonthMoviesUseCase
import com.sb.moovich.domain.usecases.home.GetTop10SeriesUseCase
import com.sb.moovich.presentation.home.ui.HomeFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMainBoardMoviesUseCase: GetMainBoardMoviesUseCase,
    private val getTop10MonthMoviesUseCase: GetTop10MonthMoviesUseCase,
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getTop10SeriesUseCase: GetTop10SeriesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomeFragmentState>(HomeFragmentState.Loading)
    val state = _state.asStateFlow()

    init {
        launch {
            _state.update {
                HomeFragmentState.Content(
                    getMainBoardMoviesUseCase(),
                    getTop10MonthMoviesUseCase(),
                    getRecommendedMoviesUseCase(),
                    getCollectionsUseCase(),
                    getTop10SeriesUseCase(),
                    getGenresUseCase()
                )
            }
        }
    }
}
