package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class FindMovieWithFilterUseCase @Inject constructor(
    private val repository: RemoteMovieRepository,
) {
    suspend operator fun invoke(
        filter: Filter,
        count: Int,
    ) = repository.findMoviesWithFilter(filter, count)
}