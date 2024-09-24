package com.sb.moovich.domain.usecases.movie

import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieById(movieId)
}
