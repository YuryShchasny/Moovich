package com.sb.moovich.domain.repository
import com.sb.moovich.domain.entity.Collection
import com.sb.moovich.domain.entity.GetAllType
import com.sb.moovich.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getRecommendedMovies(): List<Movie>

    suspend fun getMainBoardMovies(): List<Movie>

    suspend fun getTop10MonthMovies(): List<Movie>

    suspend fun getCollections(): List<Collection>

    suspend fun getTop10Series(): List<Movie>

    suspend fun getMovieById(id: Int): Movie?

    suspend fun getAllMovies(type: GetAllType): Flow<List<Movie>>

    suspend fun getAllCollections(): Flow<List<Collection>>

    suspend fun movieNextPage()

    suspend fun collectionNextPage()
}