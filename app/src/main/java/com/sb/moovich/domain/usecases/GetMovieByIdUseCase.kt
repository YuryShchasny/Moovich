package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int) = repository.getMovieById(movieId)
}