package com.sb.moovich.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sb.moovich.data.local.dbo.ActorDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actorDbo: ActorDbo)

    @Query("SELECT * FROM actors WHERE id = :actorId LIMIT 1")
    fun getActorById(actorId: Int): ActorDbo?
}