package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class FindMovieUseCase @Inject constructor(
    private val repository: RemoteMovieRepository,
) {
    suspend operator fun invoke(
        name: String,
        count: Int,
    ) = repository.findMovie(name, count)
}
