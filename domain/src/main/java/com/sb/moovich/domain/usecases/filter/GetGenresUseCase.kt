package com.sb.moovich.domain.usecases.filter

import com.sb.moovich.domain.repository.SearchFilterRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: SearchFilterRepository,
) {
    suspend operator fun invoke() = repository.getGenres()
}