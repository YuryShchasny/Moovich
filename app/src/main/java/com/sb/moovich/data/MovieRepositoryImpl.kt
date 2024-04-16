package com.sb.moovich.data

import com.sb.moovich.data.database.MovieDao
import com.sb.moovich.data.mappers.MovieMapper
import com.sb.moovich.data.mappers.ShortInfoMovieMapper
import com.sb.moovich.data.network.MovieApi
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import com.sb.moovich.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val shortInfoMovieMapper: ShortInfoMovieMapper,
    private val movieMapper: MovieMapper
) : MovieRepository {
    override fun getRecommendedMovies(): Flow<List<ShortMovieInfo>> {
        return flow {
            val response = movieApi.getRecommendedMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(shortInfoMovieMapper.mapListDtoToListEntity(it.docs ?: listOf()))
                }
            }
        }
    }

    override fun getMovieById(id: Int): Flow<MovieInfo> {
        return flow {
            val response = movieApi.getMovieById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(movieMapper.mapDtoToEntity(it))
                }
            }
        }
    }

    override fun getMovieByIdFromDatabase(id: Int): Flow<ShortMovieInfo?> {
        val movie = movieDao.getMovieById(id)
        movie?.let {
            return flow { emit(shortInfoMovieMapper.mapDtoToEntity(it)) }
        }
        return flow { emit(null) }
    }

    override fun getWatchMovies(): Flow<List<ShortMovieInfo>> =
        movieDao.getWatchMovies().map { shortInfoMovieMapper.mapListDtoToListEntity(it) }

    override suspend fun addMovieToWatchList(movie: ShortMovieInfo) {
        movieDao.insertMovie(shortInfoMovieMapper.mapEntityToDto(movie))
    }

    override suspend fun deleteMovieFromWatchList(movie: ShortMovieInfo) {
        movieDao.deleteMovie(shortInfoMovieMapper.mapEntityToDto(movie))
    }
}