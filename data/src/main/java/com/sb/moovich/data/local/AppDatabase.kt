package com.sb.moovich.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sb.moovich.data.local.converters.StringListConverter
import com.sb.moovich.data.local.converters.IntListConverter
import com.sb.moovich.data.local.converters.RoleConverter
import com.sb.moovich.data.local.dao.ActorDao
import com.sb.moovich.data.local.dao.MessageDao
import com.sb.moovich.data.local.dao.RecentMovieDao
import com.sb.moovich.data.local.dao.WatchMovieDao
import com.sb.moovich.data.local.dbo.ActorDbo
import com.sb.moovich.data.local.dbo.MessageDbo
import com.sb.moovich.data.local.dbo.RecentMovieDbo
import com.sb.moovich.data.local.dbo.WatchMovieDbo

@Database(
    entities = [RecentMovieDbo::class, WatchMovieDbo::class, ActorDbo::class, MessageDbo::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(StringListConverter::class, IntListConverter::class, RoleConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchMovieDao(): WatchMovieDao
    abstract fun recentMovieDao(): RecentMovieDao
    abstract fun actorDao(): ActorDao
    abstract fun messagesDao(): MessageDao
}
