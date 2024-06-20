package com.sb.moovich.data.database.converters

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String?> {
        if (value == null) {
            return listOf(null)
        }
        return value.split(", ").map { it.ifBlank { null } }
    }

    @TypeConverter
    fun fromMovieRating(stringList: List<String?>): String = stringList.filterNotNull().joinToString(separator = ", ")
}
