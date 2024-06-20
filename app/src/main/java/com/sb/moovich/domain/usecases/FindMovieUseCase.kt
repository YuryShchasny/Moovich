package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class FindMovieUseCase
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) {
        operator fun invoke(
            name: String,
            count: Int,
        ) = repository.findMovie(name, count)
    }
