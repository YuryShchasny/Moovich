package com.sb.moovich.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sb.moovich.data.database.converters.MoviePosterConverter
import com.sb.moovich.data.database.converters.MovieRatingConverter
import com.sb.moovich.data.model.ShortMovieInfoDto

@Database(
    entities = [ShortMovieInfoDto::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MovieRatingConverter::class, MoviePosterConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}