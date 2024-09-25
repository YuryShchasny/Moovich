package com.sb.moovich.data.remote.datasource

import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.domain.entity.DataResult
import com.sb.moovich.domain.entity.ErrorType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataSource @Inject constructor(
    @MovieApiProvide private val movieApi: MovieApi,
) {
    suspend fun checkToken(): DataResult {
        val response = movieApi.checkToken()
        return when (val code = response.code()) {
            200 -> DataResult.Success
            401 -> DataResult.Error(ErrorType.NotAuthorized)
            403 -> DataResult.Error(ErrorType.RequestLimit)
            else -> {
                if (code.toString().first() == '5')
                    DataResult.Error(ErrorType.ServerError)
                else DataResult.Error(ErrorType.Another)
            }
        }
    }
}