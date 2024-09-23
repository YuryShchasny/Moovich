package com.sb.moovich.domain.usecases.watch

import com.sb.moovich.domain.repository.WatchMovieRepository
import javax.inject.Inject

class GetWatchMovieByIdUseCase @Inject constructor(
    private val repository: WatchMovieRepository,
) {
    suspend operator fun invoke(movieId: Int) = repository.getWatchMovieById(movieId)
}