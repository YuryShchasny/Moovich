package com.sb.moovich.data.repository

import com.sb.moovich.data.local.datasource.FilterLocalDataSource
import com.sb.moovich.data.local.datasource.MovieLocalDataSource
import com.sb.moovich.data.remote.datasource.MovieRemoteDataSource
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.entity.MovieType
import com.sb.moovich.domain.entity.SortType
import com.sb.moovich.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val filterLocalDataSource: FilterLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
) : SearchRepository {

    override suspend fun getRecentMovies(): List<Movie> = movieLocalDataSource.getRecentMovies()

    override suspend fun addMovieToRecentList(movie: Movie) =
        movieLocalDataSource.addMovieToRecentList(movie)

    override suspend fun saveFilter(filter: Filter) = filterLocalDataSource.saveFilter(filter)

    override suspend fun getGenres(): List<String> = filterLocalDataSource.getGenres()

    override suspend fun getCountries(): List<String> = filterLocalDataSource.getCountries()

    override suspend fun getFilter(): Filter = filterLocalDataSource.getFilter()

    override suspend fun findMovie(
        name: String,
        count: Int,
    ): List<Movie> {
        return movieRemoteDataSource.findMovie(name, count)
    }

    override suspend fun findMoviesWithFilter(filter: Filter): Flow<List<Movie>> {
        val type = when (filter.type) {
            MovieType.ALL -> null
            MovieType.MOVIES -> "movie"
            MovieType.SERIES -> "tv-series"
            MovieType.CARTOONS -> "cartoon"
        }
        val sortType = when (filter.sortType) {
            SortType.POPULARITY -> "votes.kp"
            SortType.LATEST -> "year"
            SortType.RATING -> "rating.kp"
        }
        return movieRemoteDataSource.findMoviesWithFilter(filter, sortType, type)
    }

    override suspend fun movieNextPage() {
        movieRemoteDataSource.movieNextPage()
    }
}