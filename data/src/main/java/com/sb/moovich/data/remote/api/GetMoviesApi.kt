package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.MovieDocDto
import com.sb.moovich.data.remote.dto.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetMoviesApi {

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int,
        @Query("selectFields") selectFields: List<String> = listOf(
            "id",
            "name",
            "alternativeName",
            "description",
            "rating",
            "poster",
            "year",
            "movieLength",
            "genres",
            "actors",
            "similarMovies"
        ),
    ): Response<MovieDto>

    @GET("movie")
    suspend fun getMoviesByGenre(
        @Query("genres.name") genre: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: String = "-1",
        @Query("selectFields") selectFields: List<String> = listOf(
            "id",
            "name",
            "alternativeName",
            "description",
            "rating",
            "poster",
            "year",
            "movieLength",
            "genres"
        ),
    ): Response<MovieDocDto>

    @GET("movie")
    suspend fun getMoviesByCollection(
        @Query("lists") collection: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("selectFields") selectFields: List<String> = listOf(
            "id",
            "name",
            "alternativeName",
            "description",
            "rating",
            "poster",
            "year",
            "movieLength",
            "genres"
        ),
    ): Response<MovieDocDto>
}
