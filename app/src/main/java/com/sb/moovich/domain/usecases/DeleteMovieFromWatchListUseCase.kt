package com.sb.moovich.domain.usecases

import com.sb.moovich.domain.entity.MediumMovieInfo
import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieFromWatchListUseCase
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) {
        suspend operator fun invoke(movie: MediumMovieInfo) = repository.deleteMovieFromWatchList(movie)
    }
