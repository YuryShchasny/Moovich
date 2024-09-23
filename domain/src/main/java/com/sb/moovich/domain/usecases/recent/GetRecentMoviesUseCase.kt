package com.sb.moovich.domain.usecases.recent

import com.sb.moovich.domain.repository.SearchRepository
import javax.inject.Inject

class GetRecentMoviesUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke() = repository.getRecentMovies()
}
