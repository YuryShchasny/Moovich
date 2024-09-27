package com.sb.moovich.domain.usecases.find

import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.repository.SearchRepository
import javax.inject.Inject

class FindMovieWithFilterUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(filter: Filter) = repository.findMoviesWithFilter(filter)
}