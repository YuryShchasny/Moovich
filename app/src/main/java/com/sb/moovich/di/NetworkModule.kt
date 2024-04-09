package com.sb.moovich.di

import com.sb.moovich.data.network.MovieApi
import com.sb.moovich.data.network.MovieRepositoryImpl
import com.sb.moovich.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
interface NetworkModule {

    @Binds
    @ApplicationScope
    fun bindMovieRepository(impl: MovieRepositoryImpl) : MovieRepository

    companion object {

        private const val KINOPOISK_API_URL = "https://api.kinopoisk.dev/v1.4/"

        @Provides
        @ApplicationScope
        fun provideRetrofit() : Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(KINOPOISK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideAMovieApi(retrofit: Retrofit) : MovieApi {
            return retrofit.create(MovieApi::class.java)
        }
    }
}