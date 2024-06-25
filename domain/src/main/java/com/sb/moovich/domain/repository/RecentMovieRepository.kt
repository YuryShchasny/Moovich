package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface RecentMovieRepository {
    suspend fun getRecentMovies(): List<Movie>

    suspend fun addMovieToRecentList(movie: Movie)
}