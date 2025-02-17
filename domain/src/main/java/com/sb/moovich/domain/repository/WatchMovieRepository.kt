package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface WatchMovieRepository {
    suspend fun getWatchMovies(): List<Movie>

    suspend fun addMovieToWatchList(movie: Movie)

    suspend fun deleteMovieFromWatchList(movieId: Int)

    suspend fun getWatchMovieById(movieId: Int): Movie?
}
