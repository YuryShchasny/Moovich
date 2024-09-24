package com.sb.moovich.data.remote.datasource

import com.sb.moovich.data.di.FakeMovieApiProvide
import com.sb.moovich.data.mapper.MovieDtoMapper
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.data.remote.pagination.IPaginator
import com.sb.moovich.data.remote.pagination.Paginator
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.entity.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    @FakeMovieApiProvide private val movieApi: MovieApi,
    private val movieDtoMapper: MovieDtoMapper,
) {
    private var moviePaginator: IPaginator<Movie>? = null

    suspend fun getRecommendedMovies(): List<Movie> {
        val response = movieApi.getRecommendedMovies()
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntityList(it.docs ?: emptyList())
            }
        }
        return emptyList()
    }

    suspend fun getMainBoardMovies(): List<Movie> {
        val response = movieApi.getTop5Movies()
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntityList(it.docs ?: emptyList())
            }
        }
        return emptyList()
    }

    suspend fun getTop10MonthMovies(): List<Movie> {
        val response = movieApi.getTop10MonthMovies()
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntityList(it.docs ?: emptyList())
            }
        }
        return emptyList()
    }

    suspend fun getTop10Series(): List<Movie> {
        val response = movieApi.getTop10Series()
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntityList(it.docs ?: emptyList())
            }
        }
        return emptyList()
    }

    suspend fun getMovieById(id: Int): Movie? {
        val response = movieApi.getMovieById(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return movieDtoMapper.mapDataToEntity(it)
            }
        }
        return null
    }

    suspend fun getAllMovies(type: GetAllType): Flow<List<Movie>> {
        moviePaginator = when (type) {
            is GetAllType.Genre -> Paginator(
                request = { page ->
                    movieApi.getMoviesByGenre(genre = type.genre.lowercase(), page = page)
                        .body()?.docs
                        ?: emptyList()
                },
                mapper = movieDtoMapper,
            )

            GetAllType.Recommendations -> Paginator(
                request = { page ->
                    movieApi.getRecommendedMovies(page = page).body()?.docs ?: emptyList()
                },
                mapper = movieDtoMapper,
            )

            GetAllType.Series -> Paginator(
                request = { page ->
                    movieApi.getTop10Series(page = page).body()?.docs ?: emptyList()
                },
                mapper = movieDtoMapper,
            )

            is GetAllType.Collection -> Paginator(
                request = { page ->
                    movieApi.getMoviesByCollection(collection = type.slug, page = page).body()?.docs
                        ?: emptyList()
                },
                mapper = movieDtoMapper
            )
        }
        return moviePaginator!!.data
    }

    suspend fun findMovie(
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

    suspend fun findMoviesWithFilter(
        filter: Filter,
        count: Int,
        sortType: String,
        type: String?
    ): Flow<List<Movie>> {
        moviePaginator = Paginator(
            request = { page ->
                movieApi.filterMovie(
                    page = page,
                    limit = count,
                    sortField = sortType,
                    type = type,
                    year = "${filter.yearFrom}-${filter.yearTo}",
                    rating = "${filter.ratingFrom}-${filter.ratingTo}",
                    genres = filter.genres.map { it.lowercase() }.toTypedArray(),
                    countries = filter.countries.map { "+$it" }.toTypedArray(),
                ).body()?.docs ?: emptyList()
            },
            mapper = movieDtoMapper,
        )
        return moviePaginator!!.data
    }

    suspend fun movieNextPage() {
        moviePaginator?.nextPage()
    }
}