package com.sb.moovich.data.remote.interceptor

import com.sb.moovich.data.local.datasource.AuthLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    authLocalDataSource: AuthLocalDataSource
) : Interceptor {

    private var token = ""

    init {
        CoroutineScope(Dispatchers.IO).launch {
            authLocalDataSource.subscribeToken().collect {
                token = it
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("X-API-KEY", token)
            .build()
        return chain.proceed(request)
    }
}
