package com.sb.moovich.di

import android.app.Application
import androidx.room.Room
import com.sb.moovich.data.di.FakeMovieApiProvide
import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.local.AppDatabase
import com.sb.moovich.data.local.dao.ActorDao
import com.sb.moovich.data.local.dao.RecentMovieDao
import com.sb.moovich.data.local.dao.WatchMovieDao
import com.sb.moovich.data.remote.api.FakeMovieApi
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.data.repository.RecentMovieRepositoryImpl
import com.sb.moovich.data.repository.RemoteMovieRepositoryImpl
import com.sb.moovich.data.repository.WatchMovieRepositoryImpl
import com.sb.moovich.domain.repository.RecentMovieRepository
import com.sb.moovich.domain.repository.RemoteMovieRepository
import com.sb.moovich.domain.repository.WatchMovieRepository
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
    fun bindRemoteMovieRepository(impl: RemoteMovieRepositoryImpl): RemoteMovieRepository

    @Binds
    @Singleton
    fun bindRecentMovieRepository(impl: RecentMovieRepositoryImpl): RecentMovieRepository

    @Binds
    @Singleton
    fun bindWatchMovieRepository(impl: WatchMovieRepositoryImpl): WatchMovieRepository

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

        @MovieApiProvide
        @Provides
        @Singleton
        fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

        @FakeMovieApiProvide
        @Provides
        @Singleton
        fun provideFakeMovieApi(): MovieApi = FakeMovieApi()

        @Provides
        @Singleton
        fun provideActorDao(database: AppDatabase): ActorDao = database.actorDao()

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
