package com.sb.moovich.data.repository

import com.sb.moovich.data.local.dao.WatchMovieDao
import com.sb.moovich.data.mapper.WatchMovieDboMapper
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.WatchMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchMovieRepositoryImpl @Inject constructor(
    private val watchMovieDao: WatchMovieDao,
    private val watchMovieDboMapper: WatchMovieDboMapper
) : WatchMovieRepository {
    override suspend fun getWatchMovies(): List<Movie> {
        return watchMovieDao.getMovies().map {
            watchMovieDboMapper.mapDataToEntity(it)
        }
    }

    override suspend fun addMovieToWatchList(movie: Movie) {
        watchMovieDao.insertMovie(watchMovieDboMapper.mapEntityToData(movie))
    }

    override suspend fun deleteMovieFromWatchList(movie: Movie) {
        watchMovieDao.deleteMovie(watchMovieDboMapper.mapEntityToData(movie))
    }
}