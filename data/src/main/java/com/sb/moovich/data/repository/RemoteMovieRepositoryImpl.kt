package com.sb.moovich.data.repository

import com.sb.moovich.data.mapper.MovieDtoMapper
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.RemoteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteMovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDtoMapper: MovieDtoMapper
) : RemoteMovieRepository {

    override suspend fun getRecommendedMovies(): List<Movie> {
        val response = movieApi.getRecommendedMovies()
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntityList(it.docs ?: emptyList())
            }
        }
        return emptyList()
    }

    override suspend fun getMovieById(id: Int): Movie? {
        val response = movieApi.getMovieById(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntity(it)
            }
        }
        return null
    }

    override suspend fun findMovie(
        name: String,
        count: Int,
    ): List<Movie> {
        val response = movieApi.findMovie(name = name, limit = count)
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntityList(it.docs ?: listOf())
            }
        }
        return emptyList()
    }
}