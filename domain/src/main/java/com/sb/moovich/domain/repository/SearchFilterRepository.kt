package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.Filter

interface SearchFilterRepository {
    fun getFilter(): Filter
    fun saveFilter(filter: Filter)
    suspend fun getGenres(): List<String>
    suspend fun getCountries(): List<String>
}