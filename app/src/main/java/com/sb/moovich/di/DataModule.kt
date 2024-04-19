package com.sb.moovich.di

import android.app.Application
import androidx.room.Room
import com.sb.moovich.data.network.MovieApi
import com.sb.moovich.data.MovieRepositoryImpl
import com.sb.moovich.data.database.AppDatabase
import com.sb.moovich.data.database.RecentMovieDao
import com.sb.moovich.data.database.WatchMovieDao
import com.sb.moovich.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindMovieRepository(impl: MovieRepositoryImpl) : MovieRepository

    companion object {

        private const val KINOPOISK_API_URL = "https://api.kinopoisk.dev/v1.4/"

        @Provides
        @ApplicationScope
        fun provideRetrofit(application: Application) : Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
            val cache = Cache(application.cacheDir, cacheSize)
            val httpClient = OkHttpClient.Builder()
                .cache(cache)
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
        fun provideMovieApi(retrofit: Retrofit) : MovieApi {
            return retrofit.create(MovieApi::class.java)
        }

        @Provides
        @ApplicationScope
        fun provideWatchMovieDao(database: AppDatabase): WatchMovieDao {
            return database.watchMovieDao()
        }
        @Provides
        @ApplicationScope
        fun provideRecentMovieDao(database: AppDatabase): RecentMovieDao {
            return database.recentMovieDao()
        }

        @Provides
        @ApplicationScope
        fun provideAppDatabase(application: Application): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                "moovich.db"
            ).build()
        }
    }
}