package com.sb.moovich.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sb.moovich.data.model.MoviePoster
import com.sb.moovich.data.model.MovieRating

class MovieRatingConverter {
    private val gson = Gson()

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
