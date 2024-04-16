package com.sb.moovich.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sb.moovich.data.model.ShortMovieInfoDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: ShortMovieInfoDto)

    @Query("SELECT * FROM movies")
    fun getWatchMovies(): Flow<List<ShortMovieInfoDto>>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    fun getMovieById(movieId: Int): ShortMovieInfoDto?

    @Delete
    suspend fun deleteMovie(movie: ShortMovieInfoDto)
}