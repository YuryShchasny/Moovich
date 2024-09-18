package com.sb.moovich.data.repository

import com.sb.moovich.data.di.FakeMovieApiProvide
import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.mapper.MovieDtoMapper
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.entity.MovieType
import com.sb.moovich.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class RemoteMovieRepositoryImpl @Inject constructor(
    @FakeMovieApiProvide private val movieApi: MovieApi,
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

    override suspend fun findMoviesWithFilter(filter: Filter, count: Int): List<Movie> {
        val type = when(filter.type) {
            MovieType.ALL -> null
            MovieType.MOVIES -> "movie"
            MovieType.SERIES -> "tv-series"
            MovieType.CARTOONS -> "cartoon"
        }
        val response = movieApi.filterMovie(
            limit = count,
            type = type,
            year = "${filter.yearFrom}-${filter.yearTo}",
            rating = "${filter.ratingFrom}-${filter.ratingTo}",
            genres = filter.genres.map { it.lowercase() }.toTypedArray(),
            countries = filter.countries.map { "+$it" }.toTypedArray(),
        )
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntityList(it.docs ?: listOf())
            }
        }
        return emptyList()
    }
}