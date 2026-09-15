package com.friday.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friday.ai.data.repository.ConversationRepository
import com.friday.ai.domain.ai.AiBrain
import com.friday.ai.domain.model.ChatMessage
import com.friday.ai.domain.model.Conversation
import com.friday.ai.domain.model.ConversationMessage
import com.friday.ai.domain.model.MessageRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val aiBrain: AiBrain
) : ViewModel() {
    private val _messages = MutableStateFlow<List<ConversationMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private var currentConversationId = -1L

    fun setConversation(conversationId: Long) = viewModelScope.launch {
        currentConversationId = conversationId
        conversationRepository.getMessages(conversationId).collectLatest { _messages.value = it }
    }

    fun sendMessage(content: String) = viewModelScope.launch {
        if (currentConversationId <= 0) return@launch
        _isLoading.value = true
        _error.value = null

        try {
            val userMessage = ConversationMessage(
                conversationId = currentConversationId,
                role = "user",
                content = content
            )
            conversationRepository.addMessage(userMessage)

            val response = aiBrain.process(content, currentConversationId)
            response.onSuccess { aiResponse ->
                val assistantMessage = ConversationMessage(
                    conversationId = currentConversationId,
                    role = "assistant",
                    content = aiResponse
                )
                conversationRepository.addMessage(assistantMessage)
            }.onFailure { error ->
                _error.value = error.message ?: "Unknown error"
            }
        } finally {
            _isLoading.value = false
        }
    }

    fun clearConversation() = viewModelScope.launch {
        if (currentConversationId > 0) {
            conversationRepository.deleteAllMessages(currentConversationId)
        }
    }
}
