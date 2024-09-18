package com.sb.moovich.domain.usecases.filter

import com.sb.moovich.domain.repository.SearchFilterRepository
import javax.inject.Inject

class GetSearchFilterUseCase @Inject constructor(
    private val repository: SearchFilterRepository,
) {
    suspend operator fun invoke() = repository.getFilter()
}