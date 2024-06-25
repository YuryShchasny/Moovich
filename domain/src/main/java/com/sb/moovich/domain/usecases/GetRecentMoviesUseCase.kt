package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.repository.RecentMovieRepository
import com.sb.moovich.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class GetRecentMoviesUseCase @Inject constructor(
    private val repository: RecentMovieRepository,
) {
    suspend operator fun invoke() = repository.getRecentMovies()
}
