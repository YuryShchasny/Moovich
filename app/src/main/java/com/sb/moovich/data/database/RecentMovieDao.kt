package com.sb.moovich.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sb.moovich.data.database.model.RecentMovieDb
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: RecentMovieDb)

    @Query("SELECT * FROM recent")
    fun getMovies(): Flow<List<RecentMovieDb>>

    @Query("SELECT * FROM recent ORDER BY timestamp ASC LIMIT 1")
    fun getOldestItem(): RecentMovieDb?

    @Delete
    suspend fun deleteMovie(movie: RecentMovieDb)

    @Query("SELECT COUNT(*) FROM recent")
    fun getCount(): Int
}