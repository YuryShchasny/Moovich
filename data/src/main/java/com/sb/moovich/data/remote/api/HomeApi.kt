package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.CollectionDocDto
import com.sb.moovich.data.remote.dto.MovieDocDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface HomeApi {
    companion object {
        private const val RECOMMENDED_MOVIES_LIMIT = 20
        private val currentYear = LocalDate.now().year
    }

    @GET("movie")
    suspend fun getRecommendedMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = RECOMMENDED_MOVIES_LIMIT,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: String = "-1",
        @Query("year") releaseYearEnd: String = "${currentYear - 2}-$currentYear",
        @Query("rating.kp") rating: String = "7-10",
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
    suspend fun getTop5Movies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 5,
        @Query("sortField") sortField: String = "fees.world.value",
        @Query("sortType") sortType: String = "-1",
        @Query("year") year: String = "${currentYear - 2}-$currentYear",
        @Query("rating.kp") rating: String = "7-10",
        @Query("selectFields") selectFields: List<String> = listOf(
            "id", "name", "alternativeName", "description", "poster"
        ),
    ): Response<MovieDocDto>

    @GET("movie")
    suspend fun getTop10Series(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: String = "-1",
        @Query("lists") collection: String = "series-top250",
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

    @GET("list")
    suspend fun getCollections(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("category") category: String? = "Онлайн-кинотеатр",
        @Query("selectFields") selectFields: List<String> = listOf(
            "slug", "name", "cover", "moviesCount"
        ),
    ): Response<CollectionDocDto>

    @GET("movie")
    suspend fun getTop10MonthMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("lists") lists: String = "top10-hd",
        @Query("selectFields") selectFields: List<String> = listOf(
            "id", "poster"
        ),
    ): Response<MovieDocDto>
}
