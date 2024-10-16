package com.sb.moovich.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("actors")
data class ActorDbo(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val photo: String?,
    val name: String,
    val description: String,
)
