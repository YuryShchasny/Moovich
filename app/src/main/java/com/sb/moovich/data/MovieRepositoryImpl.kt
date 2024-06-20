package com.sb.moovich.data

import com.sb.moovich.data.database.RecentMovieDao
import com.sb.moovich.data.database.WatchMovieDao
import com.sb.moovich.data.mappers.MediumMovieInfoMapper
import com.sb.moovich.data.mappers.MovieMapper
import com.sb.moovich.data.mappers.RecentMovieMapper
import com.sb.moovich.data.mappers.ShortMovieInfoMapper
import com.sb.moovich.data.mappers.WatchMovieMapper
import com.sb.moovich.data.network.MovieApi
import com.sb.moovich.domain.entity.MediumMovieInfo
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import com.sb.moovich.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val movieApi: MovieApi,
        private val watchMovieDao: WatchMovieDao,
        private val recentMovieDao: RecentMovieDao,
        private val shortMovieInfoMapper: ShortMovieInfoMapper,
        private val watchMovieMapper: WatchMovieMapper,
        private val recentMovieMapper: RecentMovieMapper,
        private val movieMapper: MovieMapper,
        private val mediumMovieInfoMapper: MediumMovieInfoMapper,
    ) : MovieRepository {
        companion object {
            private const val MAX_RECENT_SIZE = 30
        }

        override fun getRecommendedMovies(): Flow<List<ShortMovieInfo>> =
            flow {
                val response = movieApi.getRecommendedMovies()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(shortMovieInfoMapper.mapListDtoToListEntity(it.docs ?: listOf()))
                    }
                }
            }

        override fun getMovieById(id: Int): Flow<MovieInfo> =
            flow {
                val response = movieApi.getMovieById(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(movieMapper.mapDtoToEntity(it))
                    }
                }
            }

        override fun getWatchMovieById(id: Int): Flow<MediumMovieInfo?> {
            val movie = watchMovieDao.getMovieById(id)
            movie?.let {
                return flow { emit(watchMovieMapper.mapDbToEntity(it)) }
            }
            return flow { emit(null) }
        }

        override fun getWatchMovies(): Flow<List<MediumMovieInfo>> =
            watchMovieDao.getMovies().map { watchMovieMapper.mapListDbToListEntity(it) }

        override suspend fun addMovieToWatchList(movie: MediumMovieInfo) {
            watchMovieDao.insertMovie(watchMovieMapper.mapEntityToDb(movie))
        }

        override suspend fun deleteMovieFromWatchList(movie: MediumMovieInfo) {
            watchMovieDao.deleteMovie(watchMovieMapper.mapEntityToDb(movie))
        }

        override fun getRecentMovies(): Flow<List<MediumMovieInfo>> =
            recentMovieDao.getMovies().map { movieList ->
                recentMovieMapper.mapListDbToListEntity(
                    movieList.sortedByDescending { it.timestamp },
                )
            }

        override suspend fun addMovieToRecentList(movie: MediumMovieInfo) {
            recentMovieDao.apply {
                val listSize = getCount()
                if (listSize >= MAX_RECENT_SIZE) {
                    getOldestItem()?.let {
                        deleteMovie(it)
                    }
                }
                insertMovie(recentMovieMapper.mapEntityToDb(movie))
            }
        }

        override fun findMovie(
            name: String,
            count: Int,
        ): Flow<List<MediumMovieInfo>> =
            flow {
                val response = movieApi.findMovie(name = name, limit = count)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(mediumMovieInfoMapper.mapListDtoToListEntity(it.docs ?: listOf()))
                    }
                }
            }
    }
