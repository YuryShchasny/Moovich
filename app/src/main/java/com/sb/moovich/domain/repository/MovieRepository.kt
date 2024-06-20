package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.MediumMovieInfo
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getRecommendedMovies(): Flow<List<ShortMovieInfo>>

    fun getMovieById(id: Int): Flow<MovieInfo>

    fun getWatchMovieById(id: Int): Flow<MediumMovieInfo?>

    fun getWatchMovies(): Flow<List<MediumMovieInfo>>

    suspend fun addMovieToWatchList(movie: MediumMovieInfo)

    suspend fun deleteMovieFromWatchList(movie: MediumMovieInfo)

    fun getRecentMovies(): Flow<List<MediumMovieInfo>>

    suspend fun addMovieToRecentList(movie: MediumMovieInfo)

    fun findMovie(
        name: String,
        count: Int,
    ): Flow<List<MediumMovieInfo>>
}
