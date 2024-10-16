package com.sb.moovich.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recent")
data class RecentMovieDbo(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val rating: Double,
    val poster: String?,
    val backdrop: String?,
    val movieLength: Int,
    val urlWatch: String,
    val year: Int,
    val genres: List<String>,
    val actors: List<Int>,
    val timestamp: Long,
)
