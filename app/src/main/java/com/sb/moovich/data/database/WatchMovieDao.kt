package com.sb.moovich.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sb.moovich.data.database.model.WatchMovieDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: WatchMovieDb)

    @Query("SELECT * FROM watch")
    fun getMovies(): Flow<List<WatchMovieDb>>

    @Query("SELECT * FROM watch WHERE id = :movieId LIMIT 1")
    fun getMovieById(movieId: Int): WatchMovieDb?

    @Delete
    suspend fun deleteMovie(movie: WatchMovieDb)
}
