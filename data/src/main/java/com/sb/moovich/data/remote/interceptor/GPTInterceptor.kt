package com.sb.moovich.data.remote.interceptor

import com.sb.moovich.data.di.GPTApiKeyProvide
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GPTInterceptor @Inject constructor(
    @GPTApiKeyProvide private val key: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $key")
            .build()
        return chain.proceed(request)
    }
}