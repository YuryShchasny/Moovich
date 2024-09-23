package com.sb.moovich.domain.usecases.recent

import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.SearchRepository
import javax.inject.Inject

class AddMovieToRecentUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(movie: Movie) = repository.addMovieToRecentList(movie)
}
