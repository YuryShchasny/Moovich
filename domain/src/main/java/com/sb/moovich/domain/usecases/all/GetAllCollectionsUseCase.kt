package com.sb.moovich.domain.usecases.all

import com.sb.moovich.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllCollectionsUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke() = repository.getAllCollections()
}