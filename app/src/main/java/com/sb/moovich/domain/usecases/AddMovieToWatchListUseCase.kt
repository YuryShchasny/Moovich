package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.entity.ShortMovieInfo
import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class AddMovieToWatchListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: ShortMovieInfo) = repository.addMovieToWatchList(movie)
}