package com.sb.moovich.data.remote.interceptor

import com.sb.moovich.data.local.datasource.AuthLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authLocalDataSource.token
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("X-API-KEY", token)
            .build()
        return chain.proceed(request)
    }
}
