package com.sb.moovich.data.repository

import com.sb.moovich.data.local.datasource.MovieLocalDataSource
import com.sb.moovich.data.local.dao.WatchMovieDao
import com.sb.moovich.data.mapper.WatchMovieDboMapper
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.WatchMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchMovieRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
) : WatchMovieRepository {
    override suspend fun getWatchMovies(): List<Movie> = movieLocalDataSource.getWatchMovies()

    override suspend fun addMovieToWatchList(movie: Movie) {
        movieLocalDataSource.addMovieToWatchList(movie)
    }

    override suspend fun deleteMovieFromWatchList(movieId: Int) {
        movieLocalDataSource.deleteMovieFromWatchList(movieId)
    }

    override suspend fun getWatchMovieById(movieId: Int): Movie? {
        return movieLocalDataSource.getMovieFromWatchList(movieId)
    }
}