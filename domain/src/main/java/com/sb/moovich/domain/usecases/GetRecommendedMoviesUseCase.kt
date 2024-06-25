package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class GetRecommendedMoviesUseCase @Inject constructor(
    private val repository: RemoteMovieRepository,
) {
    suspend operator fun invoke() = repository.getRecommendedMovies()
}
