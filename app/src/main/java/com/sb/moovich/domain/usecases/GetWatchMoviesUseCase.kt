package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class GetWatchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getWatchMovies()
}