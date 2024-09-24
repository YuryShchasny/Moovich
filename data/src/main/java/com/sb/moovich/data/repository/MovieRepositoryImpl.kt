package com.sb.moovich.data.repository

import com.sb.moovich.data.local.datasource.MovieLocalDataSource
import com.sb.moovich.data.remote.datasource.CollectionRemoteDataSource
import com.sb.moovich.data.remote.datasource.MovieRemoteDataSource
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.entity.Movie
import com.sb.moovich.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val collectionRemoteDataSource: CollectionRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getRecommendedMovies(): List<Movie> {
        return movieRemoteDataSource.getRecommendedMovies()
    }

    override suspend fun getMainBoardMovies(): List<Movie> {
        return movieRemoteDataSource.getMainBoardMovies()
    }

    override suspend fun getTop10MonthMovies(): List<Movie> {
        return movieRemoteDataSource.getTop10MonthMovies()
    }

    override suspend fun getCollections(): List<Collection> {
        return collectionRemoteDataSource.getCollections()
    }

    override suspend fun getTop10Series(): List<Movie> {
        return movieRemoteDataSource.getTop10Series()
    }

    override suspend fun getMovieById(id: Int): Movie? {
        return movieLocalDataSource.getMovieFromWatchList(id)
            ?: movieLocalDataSource.getMovieFromRecentList(id)
            ?: movieRemoteDataSource.getMovieById(id)
    }

    override suspend fun getAllMovies(type: GetAllType): Flow<List<Movie>> {
        return movieRemoteDataSource.getAllMovies(type)
    }

    override suspend fun getAllCollections(): Flow<List<Collection>> {
        return collectionRemoteDataSource.getAllCollections()
    }

    override suspend fun movieNextPage() {
        movieRemoteDataSource.movieNextPage()
    }

    override suspend fun collectionNextPage() {
        collectionRemoteDataSource.collectionNextPage()
    }
}