package com.sb.moovich.di

import android.app.Application
import androidx.room.Room
import com.sb.moovich.data.MovieRepositoryImpl
import com.sb.moovich.data.database.AppDatabase
import com.sb.moovich.data.database.RecentMovieDao
import com.sb.moovich.data.database.WatchMovieDao
import com.sb.moovich.data.network.MovieApi
import com.sb.moovich.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    companion object {
        private const val KINOPOISK_API_URL = "https://api.kinopoisk.dev/v1.4/"

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClient =
                OkHttpClient
                    .Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build()

            return Retrofit
                .Builder()
                .baseUrl(KINOPOISK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }

        @Provides
        @Singleton
        fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

        @Provides
        @Singleton
        fun provideWatchMovieDao(database: AppDatabase): WatchMovieDao = database.watchMovieDao()

        @Provides
        @Singleton
        fun provideRecentMovieDao(database: AppDatabase): RecentMovieDao = database.recentMovieDao()

        @Provides
        @Singleton
        fun provideAppDatabase(application: Application): AppDatabase =
            Room
                .databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    "moovich.db",
                ).build()
    }
}
