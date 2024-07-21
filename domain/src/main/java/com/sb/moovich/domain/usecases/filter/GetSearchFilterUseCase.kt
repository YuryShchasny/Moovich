package com.sb.moovich.domain.usecases.filter

import com.sb.moovich.domain.repository.SearchFilterRepository
import javax.inject.Inject

class GetSearchFilterUseCase @Inject constructor(
    private val repository: SearchFilterRepository,
) {
    operator fun invoke() = repository.getFilter()
}