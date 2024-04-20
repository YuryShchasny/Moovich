package com.sb.moovich.data.network

import com.sb.moovich.data.network.model.MediumMovieDocDto
import com.sb.moovich.data.network.model.MovieInfoDto
import com.sb.moovich.data.network.model.ShortMovieDocDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        private const val RECOMMENDED_MOVIES_LIMIT = 10
        private const val SEARCH_MOVIES_LIMIT = 10
        private const val API_KEY = "R0V497J-ZGYMFXX-JJ9QSFS-96AN48G"
    }

    @GET("movie")
    suspend fun getRecommendedMovies(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = RECOMMENDED_MOVIES_LIMIT,
        @Query("selectFields") selectFields: List<String> = listOf(
            "id",
            "name",
            "rating",
            "poster"
        ),
        @Query("releaseYears.end") releaseYearEnd: String = "2023-2024",
        @Query("rating.kp") rating: String = "8-10"
    ): Response<ShortMovieDocDto>

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): Response<MovieInfoDto>

    @GET("movie/search")
    suspend fun findMovie(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = SEARCH_MOVIES_LIMIT,
        @Query("query") name: String
    ): Response<MediumMovieDocDto>
}