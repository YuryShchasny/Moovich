package com.sb.moovich.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.sb.moovich.BuildConfig
import com.sb.moovich.data.di.FakeMovieApiProvide
import com.sb.moovich.data.di.GPTApiKeyProvide
import com.sb.moovich.data.di.MovieApiProvide
import com.sb.moovich.data.local.AppDatabase
import com.sb.moovich.data.local.dao.ActorDao
import com.sb.moovich.data.local.dao.MessageDao
import com.sb.moovich.data.local.dao.RecentMovieDao
import com.sb.moovich.data.local.dao.WatchMovieDao
import com.sb.moovich.data.remote.api.FakeMovieApi
import com.sb.moovich.data.remote.api.GPTApi
import com.sb.moovich.data.remote.api.MovieApi
import com.sb.moovich.data.remote.interceptor.GPTInterceptor
import com.sb.moovich.data.remote.interceptor.TokenInterceptor
import com.sb.moovich.data.repository.AuthRepositoryImpl
import com.sb.moovich.data.repository.GPTRepositoryImpl
import com.sb.moovich.data.repository.MovieRepositoryImpl
import com.sb.moovich.data.repository.SearchRepositoryImpl
import com.sb.moovich.data.repository.WatchMovieRepositoryImpl
import com.sb.moovich.domain.repository.AuthRepository
import com.sb.moovich.domain.repository.GPTRepository
import com.sb.moovich.domain.repository.MovieRepository
import com.sb.moovich.domain.repository.SearchRepository
import com.sb.moovich.domain.repository.WatchMovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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

    @Binds
    @Singleton
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    fun bindWatchMovieRepository(impl: WatchMovieRepositoryImpl): WatchMovieRepository

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindGPTRepository(impl: GPTRepositoryImpl): GPTRepository

    companion object {

        @Provides
        @GPTApiKeyProvide
        fun provideGPTKey(): String = BuildConfig.GPT_API_KEY

        @Provides
        @Singleton
        fun provideHttpClient(
            interceptor: TokenInterceptor
        ): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        @Provides
        @Singleton
        fun provideRetrofit(
            okHttpClient: OkHttpClient
        ): Retrofit = Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        @Provides
        @Singleton
        fun provideGPTApi(
            interceptor: GPTInterceptor
        ): GPTApi {
            val client = OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
            return Retrofit
                .Builder()
                .baseUrl(BuildConfig.GPT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(GPTApi::class.java)
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
        fun provideMessageDao(database: AppDatabase): MessageDao = database.messagesDao()

        @Provides
        @Singleton
        fun provideAppDatabase(application: Application): AppDatabase =
            Room
                .databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    "moovich.db",
                ).build()

        @Provides
        @Singleton
        fun provideSharedPreferences(application: Application): SharedPreferences {
            return application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        }
    }
}
