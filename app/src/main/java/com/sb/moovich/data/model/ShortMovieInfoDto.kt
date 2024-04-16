package com.sb.moovich.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("movies")
data class ShortMovieInfoDto(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster")
    val poster: MoviePoster?,
    @SerializedName("rating")
    val rating: MovieRating?
)