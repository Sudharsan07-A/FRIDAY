package com.friday.ai.data.repository

import com.friday.ai.data.database.dao.ConversationDao
import com.friday.ai.domain.model.Conversation
import com.friday.ai.domain.model.ConversationMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConversationRepository @Inject constructor(
    private val conversationDao: ConversationDao
) {
    suspend fun createConversation(conversation: Conversation): Long = 
        conversationDao.insertConversation(conversation)
    
    suspend fun updateConversation(conversation: Conversation) = 
        conversationDao.updateConversation(conversation)
    
    suspend fun deleteConversation(conversation: Conversation) = 
        conversationDao.deleteConversation(conversation)
    
    suspend fun getConversation(id: Long) = conversationDao.getConversationById(id)
    
    fun getAllConversations(): Flow<List<Conversation>> = conversationDao.getAllConversations()
    
    suspend fun addMessage(message: ConversationMessage): Long = 
        conversationDao.insertMessage(message)
    
    fun getMessages(conversationId: Long): Flow<List<ConversationMessage>> = 
        conversationDao.getMessages(conversationId)
    
    suspend fun deleteAllMessages(conversationId: Long) = 
        conversationDao.deleteAllMessages(conversationId)
    
    suspend fun deleteAll() = conversationDao.deleteAllConversations()
}
