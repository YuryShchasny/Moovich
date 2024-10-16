package com.sb.moovich.data.remote.datasource

import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.data.utils.process
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataSource @Inject constructor(
    @MovieApiProvide private val movieApi: MovieApi,
) {
    suspend fun checkToken(): Boolean {
        return movieApi.checkToken().process { it != null }
    }
}
