package com.sb.moovich.data.repository

import com.sb.moovich.data.local.dao.RecentMovieDao
import com.sb.moovich.data.mapper.RecentMovieDboMapper
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.RecentMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentMovieRepositoryImpl @Inject constructor(
    private val recentMovieDao: RecentMovieDao,
    private val recentMovieDboMapper: RecentMovieDboMapper
) : RecentMovieRepository {
    companion object {
        private const val MAX_RECENT_SIZE = 30
    }

    override suspend fun getRecentMovies(): List<Movie> {
        return recentMovieDao.getMovies()
            .sortedByDescending { it.timestamp }
            .map { recentMovieDboMapper.mapDataToEntity(it) }
    }


    override suspend fun addMovieToRecentList(movie: Movie) {
        recentMovieDao.apply {
            if (getCount() >= MAX_RECENT_SIZE) {
                getOldestItem()?.let { notNullOldestItem ->
                    deleteMovie(notNullOldestItem)
                }
            }
            insertMovie(recentMovieDboMapper.mapEntityToData(movie))
        }

    }
}