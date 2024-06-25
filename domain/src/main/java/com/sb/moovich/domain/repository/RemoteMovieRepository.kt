package com.sb.moovich.domain.repository
import com.sb.moovich.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface RemoteMovieRepository {
    suspend fun getRecommendedMovies(): List<Movie>

    suspend fun getMovieById(id: Int): Movie?

    suspend fun findMovie(
        name: String,
        count: Int,
    ): List<Movie>
}