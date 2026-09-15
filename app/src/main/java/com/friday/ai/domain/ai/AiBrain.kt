package com.friday.ai.domain.ai

import com.friday.ai.data.repository.CodexRepository
import com.friday.ai.data.repository.ConversationRepository
import com.friday.ai.data.repository.MemoryRepository
import com.friday.ai.domain.model.AssistantContext
import com.friday.ai.domain.model.AssistantStatus
import com.friday.ai.domain.model.ChatMessage
import com.friday.ai.domain.model.MessageRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AiBrain @Inject constructor(
    private val aiProvider: AiProvider,
    private val codexRepository: CodexRepository,
    private val memoryRepository: MemoryRepository,
    private val conversationRepository: ConversationRepository
) {
    private val _status = MutableStateFlow(AssistantStatus.READY)
    val status = _status.asStateFlow()

    suspend fun process(userMessage: String, conversationId: Long): Result<String> = try {
        _status.value = AssistantStatus.THINKING

        val conversationHistory = conversationRepository.getMessages(conversationId)
            .toList()
            .map { ChatMessage(id = it.id.toString(), role = if (it.role == "user") MessageRole.USER else MessageRole.ASSISTANT, content = it.content) }

        val codexResults = codexRepository.search(userMessage)
        val memories = memoryRepository.getMemory(conversationId) ?.let { listOf(it) } ?: emptyList()

        val context = AssistantContext(
            conversationHistory = conversationHistory,
            relevantCodexEntries = codexResults,
            relevantMemories = memories
        )

        val messages = conversationHistory + ChatMessage(
            role = MessageRole.USER,
            content = userMessage
        )

        val response = aiProvider.chat(messages, context)
        _status.value = AssistantStatus.READY

        response.map { it.text }
    } catch (e: Exception) {
        _status.value = AssistantStatus.ERROR
        Result.failure(e)
    }

    fun setStatus(status: AssistantStatus) {
        _status.value = status
    }
}
