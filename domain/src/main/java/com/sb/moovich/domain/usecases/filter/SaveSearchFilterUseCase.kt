package com.sb.moovich.domain.usecases.filter

import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.repository.SearchFilterRepository
import javax.inject.Inject

class SaveSearchFilterUseCase @Inject constructor(
    private val repository: SearchFilterRepository,
) {
    suspend operator fun invoke(filter: Filter) = repository.saveFilter(filter)
}