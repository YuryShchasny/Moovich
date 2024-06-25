package com.sb.moovich.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sb.moovich.data.local.dbo.RecentMovieDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: RecentMovieDbo)

    @Query("SELECT * FROM recent")
    fun getMovies(): List<RecentMovieDbo>

    @Query("SELECT * FROM recent ORDER BY timestamp ASC LIMIT 1")
    fun getOldestItem(): RecentMovieDbo?

    @Delete
    suspend fun deleteMovie(movie: RecentMovieDbo)

    @Query("SELECT COUNT(*) FROM recent")
    fun getCount(): Int
}
