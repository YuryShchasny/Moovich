package com.sb.moovich.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sb.moovich.data.local.dbo.WatchMovieDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: WatchMovieDbo)

    @Query("SELECT * FROM watch")
    fun getMovies(): List<WatchMovieDbo>

    @Delete
    suspend fun deleteMovie(movie: WatchMovieDbo)
}
