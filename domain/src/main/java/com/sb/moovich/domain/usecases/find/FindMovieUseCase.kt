package com.sb.moovich.domain.usecases.find

import com.sb.moovich.domain.repository.SearchRepository
import javax.inject.Inject

class FindMovieUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(
        name: String,
        count: Int,
    ) = repository.findMovie(name, count)
}
