package com.sb.moovich.data.remote.datasource

import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.remote.api.MovieApi
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor (
    @MovieApiProvide private val movieApi: MovieApi,
) {
    suspend fun checkToken(): Boolean {
        val response = movieApi.checkToken()
        return response.code() == 200
    }
}