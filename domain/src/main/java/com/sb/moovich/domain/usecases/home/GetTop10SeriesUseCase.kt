package com.sb.moovich.domain.usecases.home

import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class GetTop10SeriesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke() = repository.getTop10Series()
}