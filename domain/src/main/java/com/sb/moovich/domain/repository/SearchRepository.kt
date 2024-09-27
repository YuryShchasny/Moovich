package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getRecentMovies(): List<Movie>

    suspend fun addMovieToRecentList(movie: Movie)

    suspend fun getFilter(): Filter

    suspend fun saveFilter(filter: Filter)

    suspend fun getGenres(): List<String>

    suspend fun getCountries(): List<String>

    suspend fun findMovie(
        name: String,
        count: Int,
    ): List<Movie>

    suspend fun findMoviesWithFilter(filter: Filter): Flow<List<Movie>>

    suspend fun movieNextPage()
}