package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.MovieDocDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    companion object {
        private const val SEARCH_MOVIES_LIMIT = 10
    }

    @GET("movie")
    suspend fun filterMovie(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = SEARCH_MOVIES_LIMIT,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortType") sortType: String = "-1",
        @Query("type") type: String?,
        @Query("year") year: String,
        @Query("rating.kp") rating: String,
        @Query("genres.name") genres: Array<String>,
        @Query("countries.name") countries: Array<String>,
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

    @GET("movie/search")
    suspend fun findMovie(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = SEARCH_MOVIES_LIMIT,
        @Query("query") name: String,
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
