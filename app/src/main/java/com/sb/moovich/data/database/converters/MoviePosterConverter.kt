package com.sb.moovich.data.database.converters

import androidx.room.TypeConverter
import com.sb.moovich.data.model.MoviePoster

class MoviePosterConverter {
    @TypeConverter
    fun fromString(value: String?): MoviePoster? {
        if (value == null) {
            return null
        }
        return MoviePoster(value)
    }

    @TypeConverter
    fun fromMoviePoster(poster: MoviePoster?): String? {
        return poster?.previewUrl
    }
}
