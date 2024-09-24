package com.sb.moovich.data.local.datasource

import com.sb.moovich.data.local.dao.RecentMovieDao
import com.sb.moovich.data.local.dao.WatchMovieDao
import com.sb.moovich.data.mapper.RecentMovieDboMapper
import com.sb.moovich.data.mapper.WatchMovieDboMapper
import com.sb.moovich.domain.entity.Movie
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val watchMovieDao: WatchMovieDao,
    private val recentMovieDao: RecentMovieDao,
    private val recentMovieDboMapper: RecentMovieDboMapper,
    private val watchMovieDboMapper: WatchMovieDboMapper
) {
    companion object {
        private const val MAX_RECENT_SIZE = 30
    }

    suspend fun getRecentMovies(): List<Movie> {
        return recentMovieDao.getMovies()
            .sortedByDescending { it.timestamp }
            .map { recentMovieDboMapper.mapDataToEntity(it) }
    }

    suspend fun addMovieToRecentList(movie: Movie) {
        recentMovieDao.apply {
            if (getCount() >= MAX_RECENT_SIZE) {
                getOldestItem()?.let { notNullOldestItem ->
                    deleteMovie(notNullOldestItem)
                }
            }
            insertMovie(recentMovieDboMapper.mapEntityToData(movie))
        }
    }

    suspend fun getWatchMovies(): List<Movie> {
        return watchMovieDao.getMovies().map {
            watchMovieDboMapper.mapDataToEntity(it)
        }
    }

    suspend fun addMovieToWatchList(movie: Movie) {
        watchMovieDao.insertMovie(watchMovieDboMapper.mapEntityToData(movie))
    }

    suspend fun deleteMovieFromWatchList(movieId: Int) {
        watchMovieDao.deleteMovie(movieId)
    }

    suspend fun getMovieFromWatchList(id: Int): Movie? {
        return watchMovieDao.getMovieById(id)?.let {
            watchMovieDboMapper.mapDataToEntity(it)
        }
    }

    suspend fun getMovieFromRecentList(id: Int): Movie? {
        return recentMovieDao.getMovieById(id)?.let {
            recentMovieDboMapper.mapDataToEntity(it)
        }
    }
}