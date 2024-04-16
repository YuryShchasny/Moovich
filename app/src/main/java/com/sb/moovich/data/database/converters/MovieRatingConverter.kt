package com.sb.moovich.data.database.converters

import androidx.room.TypeConverter
import com.sb.moovich.data.model.MovieRating

class MovieRatingConverter {

    @TypeConverter
    fun fromString(value: String?): MovieRating? {
        if (value == null) {
            return null
        }
        return MovieRating(value.toDouble())
    }

    @TypeConverter
    fun fromMovieRating(rating: MovieRating?): String? {
        return rating?.kinopoisk?.toString()
    }
}
