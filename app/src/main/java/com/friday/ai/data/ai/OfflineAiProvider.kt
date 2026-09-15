package com.friday.ai.data.ai

import com.friday.ai.domain.ai.AiProvider
import com.friday.ai.domain.model.AiResponse
import com.friday.ai.domain.model.AssistantContext
import com.friday.ai.domain.model.ChatMessage

class OfflineAiProvider : AiProvider {
    override suspend fun chat(
        messages: List<ChatMessage>,
        context: AssistantContext,
        temperature: Float
    ): Result<AiResponse> = Result.failure(
        Exception("Offline. I can access your local Codex, notes, and memories.")
    )
}
