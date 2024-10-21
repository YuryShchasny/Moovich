package com.sb.moovich.data.remote.datasource

import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.mapper.MovieDtoMapper
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.data.remote.pagination.IPaginator
import com.sb.moovich.data.remote.pagination.Paginator
import com.sb.moovich.data.utils.process
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.entity.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
    @MovieApiProvide private val movieApi: MovieApi,
    private val movieDtoMapper: MovieDtoMapper,
) {
    private var moviePaginator: IPaginator<Movie>? = null

    suspend fun getRecommendedMovies(): List<Movie> {
        return movieApi.getRecommendedMovies().process {
            movieDtoMapper.mapDataToEntityList(it?.docs ?: emptyList())
        }
    }

    suspend fun getMainBoardMovies(): List<Movie> {
        return movieApi.getTop5Movies().process {
            movieDtoMapper.mapDataToEntityList(it?.docs ?: emptyList())
        }
    }

    suspend fun getTop10MonthMovies(): List<Movie> {
        return movieApi.getTop10MonthMovies().process {
            movieDtoMapper.mapDataToEntityList(it?.docs ?: emptyList())
        }
    }

    suspend fun getTop10Series(): List<Movie> {
        return movieApi.getTop10Series().process {
            movieDtoMapper.mapDataToEntityList(it?.docs ?: emptyList())
        }
    }

    suspend fun getMovieById(id: Int): Movie? {
        return movieApi.getMovieById(id).process {
            movieDtoMapper.mapDataToEntity(it ?: return@process null)
        }
    }

    suspend fun getAllMovies(type: GetAllType): Flow<List<Movie>> {
        moviePaginator = when (type) {
            is GetAllType.Genre -> Paginator(
                request = { page ->
                    movieApi.getMoviesByGenre(genre = type.genre.lowercase(), page = page)
                        .process()
                        .body()?.docs
                        ?: emptyList()
                },
                mapper = movieDtoMapper,
            )

            GetAllType.Recommendations -> Paginator(
                request = { page ->
                    movieApi.getRecommendedMovies(page = page)
                        .process()
                        .body()?.docs ?: emptyList()
                },
                mapper = movieDtoMapper,
            )

            GetAllType.Series -> Paginator(
                request = { page ->
                    movieApi.getTop10Series(page = page)
                        .process()
                        .body()?.docs ?: emptyList()
                },
                mapper = movieDtoMapper,
            )

            is GetAllType.Collection -> Paginator(
                request = { page ->
                    movieApi.getMoviesByCollection(collection = type.slug, page = page)
                        .process()
                        .body()?.docs ?: emptyList()
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
        return movieApi.findMovie(name = name, limit = count).process {
            movieDtoMapper.mapDataToEntityList(it?.docs ?: listOf())
        }
    }

    suspend fun findMoviesWithFilter(
        filter: Filter,
        sortType: String,
        type: String?
    ): Flow<List<Movie>> {
        moviePaginator = Paginator(
            request = { page ->
                movieApi.filterMovie(
                    page = page,
                    sortField = sortType,
                    type = type,
                    year = "${filter.yearFrom}-${filter.yearTo}",
                    rating = "${filter.ratingFrom}-${filter.ratingTo}",
                    genres = filter.genres.map { it.lowercase() }.toTypedArray(),
                    countries = filter.countries.map { "+$it" }.toTypedArray(),
                ).process().body()?.docs ?: emptyList()
            },
            mapper = movieDtoMapper,
        )
        return moviePaginator!!.data
    }

    suspend fun movieNextPage() {
        moviePaginator?.nextPage()
    }
}
