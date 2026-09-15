package com.friday.ai.domain.ai

import com.friday.ai.domain.model.AiResponse
import com.friday.ai.domain.model.AssistantContext
import com.friday.ai.domain.model.ChatMessage

interface AiProvider {
    suspend fun chat(
        messages: List<ChatMessage>,
        context: AssistantContext,
        temperature: Float = 0.7f
    ): Result<AiResponse>
}
