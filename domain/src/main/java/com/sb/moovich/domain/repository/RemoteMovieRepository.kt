package com.sb.moovich.domain.repository
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.Movie

interface RemoteMovieRepository {
    suspend fun getRecommendedMovies(): List<Movie>

    suspend fun getMovieById(id: Int): Movie?

    suspend fun findMovie(
        name: String,
        count: Int,
    ): List<Movie>

    suspend fun findMoviesWithFilter(filter: Filter, count: Int): List<Movie>
}