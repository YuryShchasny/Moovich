package com.sb.moovich.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recent")
data class RecentMovieDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double,
    val poster: String,
    val movieLength: Int,
    val year: Int,
    val genres: List<String?>,
    val timestamp: Long,
)
