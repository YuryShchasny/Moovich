package com.sb.moovich.domain.usecases.filter

import com.sb.moovich.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchFilterUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke() = repository.getFilter()
}