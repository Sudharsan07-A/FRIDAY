package com.friday.ai.data.database.dao

import androidx.room.*
import com.friday.ai.domain.model.Conversation
import com.friday.ai.domain.model.ConversationMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: Conversation): Long

    @Update
    suspend fun updateConversation(conversation: Conversation)

    @Delete
    suspend fun deleteConversation(conversation: Conversation)

    @Query("SELECT * FROM conversations WHERE id = :id")
    suspend fun getConversationById(id: Long): Conversation?

    @Query("SELECT * FROM conversations ORDER BY updatedAt DESC")
    fun getAllConversations(): Flow<List<Conversation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ConversationMessage): Long

    @Update
    suspend fun updateMessage(message: ConversationMessage)

    @Delete
    suspend fun deleteMessage(message: ConversationMessage)

    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun getMessages(conversationId: Long): Flow<List<ConversationMessage>>

    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentMessages(conversationId: Long, limit: Int): List<ConversationMessage>

    @Query("DELETE FROM messages WHERE conversationId = :conversationId")
    suspend fun deleteAllMessages(conversationId: Long)

    @Query("DELETE FROM conversations")
    suspend fun deleteAllConversations()
}
