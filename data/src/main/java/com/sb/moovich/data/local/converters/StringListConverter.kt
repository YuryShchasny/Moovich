package com.sb.moovich.data.local.converters

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(", ")
    }

    @TypeConverter
    fun fromMovieRating(stringList: List<String>): String = stringList.joinToString { it }
}
