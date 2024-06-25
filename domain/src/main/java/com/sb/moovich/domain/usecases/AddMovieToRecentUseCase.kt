package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.RecentMovieRepository
import com.sb.moovich.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class AddMovieToRecentUseCase @Inject constructor(
    private val repository: RecentMovieRepository,
) {
    suspend operator fun invoke(movie: Movie) = repository.addMovieToRecentList(movie)
}
