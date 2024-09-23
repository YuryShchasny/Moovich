package com.sb.moovich.domain.usecases.all

import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(type: GetAllType) = repository.getAllMovies(type)
}