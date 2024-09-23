package com.sb.moovich.domain.usecases.watch

import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.WatchMovieRepository
import javax.inject.Inject

class AddMovieToWatchListUseCase @Inject constructor(
    private val repository: WatchMovieRepository,
) {
    suspend operator fun invoke(movie: Movie) = repository.addMovieToWatchList(movie)
}
