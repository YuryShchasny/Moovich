package com.sb.moovich.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sb.moovich.data.database.converters.StringListConverter
import com.sb.moovich.data.database.model.RecentMovieDb
import com.sb.moovich.data.database.model.WatchMovieDb

@Database(
    entities = [RecentMovieDb::class, WatchMovieDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchMovieDao(): WatchMovieDao
    abstract fun recentMovieDao(): RecentMovieDao
}