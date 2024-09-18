package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.Filter

interface SearchFilterRepository {
    suspend fun getFilter(): Filter
    suspend fun saveFilter(filter: Filter)
    suspend fun getGenres(): List<String>
    suspend fun getCountries(): List<String>
}