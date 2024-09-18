package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.MovieDocDto
import com.sb.moovich.data.remote.dto.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        private const val RECOMMENDED_MOVIES_LIMIT = 100
        private const val SEARCH_MOVIES_LIMIT = 10
        private const val API_KEY = "R0V497J-ZGYMFXX-JJ9QSFS-96AN48G"
    }

    @GET("movie")
    suspend fun getRecommendedMovies(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = RECOMMENDED_MOVIES_LIMIT,
        @Query("sortField") sortField: String = "votes.await",
        @Query("sortType") sortType: String = "-1",
        @Query("releaseYears.end") releaseYearEnd: String = "2022-2024",
        @Query("rating.kp") rating: String = "7-10",
    ): Response<MovieDocDto>

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): Response<MovieDto>

    @GET("movie/search")
    suspend fun findMovie(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = SEARCH_MOVIES_LIMIT,
        @Query("query") name: String,
    ): Response<MovieDocDto>

    @GET("movie")
    suspend fun filterMovie(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = SEARCH_MOVIES_LIMIT,
        @Query("type") type: String?,
        @Query("year") year: String,
        @Query("rating.kp") rating: String,
        @Query("genres.name") genres: Array<String>,
        @Query("countries.name") countries: Array<String>,
    ): Response<MovieDocDto>


}
