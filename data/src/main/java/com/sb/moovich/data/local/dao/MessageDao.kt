package com.sb.moovich.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sb.moovich.data.local.dbo.MessageDbo

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(messageDbo: MessageDbo)

    @Query("SELECT * FROM messages ORDER BY date ASC")
    suspend fun getMessages(): List<MessageDbo>

    @Query("DELETE FROM messages")
    suspend fun clearAll()
}
