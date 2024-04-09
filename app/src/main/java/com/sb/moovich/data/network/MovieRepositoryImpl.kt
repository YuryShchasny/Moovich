package com.sb.moovich.data.network

import com.sb.moovich.data.mappers.MovieMapper
import com.sb.moovich.data.mappers.ShortInfoMovieMapper
import com.sb.moovich.domain.entity.MovieInfo
import com.sb.moovich.domain.entity.ShortMovieInfo
import com.sb.moovich.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val shortInfoMovieMapper: ShortInfoMovieMapper,
    private val movieMapper: MovieMapper
) : MovieRepository {
    override fun getRecommendedMovies(): Flow<List<ShortMovieInfo>> {
        return flow {
            val response = movieApi.getRecommendedMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(shortInfoMovieMapper.mapListDtoToListEntity(it.docs))
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
}