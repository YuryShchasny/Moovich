package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.MovieDocDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApi {
    @GET("movie")
    suspend fun checkToken(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 1,
        @Query("selectFields") selectFields: List<String> = listOf(
            "id",
        ),
    ): Response<MovieDocDto>
}