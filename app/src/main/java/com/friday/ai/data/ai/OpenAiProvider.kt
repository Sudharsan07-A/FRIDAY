package com.friday.ai.data.ai

import com.friday.ai.domain.ai.AiProvider
import com.friday.ai.domain.model.AiResponse
import com.friday.ai.domain.model.AssistantAction
import com.friday.ai.domain.model.AssistantContext
import com.friday.ai.domain.model.ChatMessage
import com.friday.ai.domain.model.MessageRole
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

@Serializable
data class OpenAiRequest(
    val model: String,
    val messages: List<OpenAiMessage>,
    val temperature: Float = 0.7f,
    val max_tokens: Int = 1000
)

@Serializable
data class OpenAiMessage(
    val role: String,
    val content: String
)

@Serializable
data class OpenAiResponse(
    val choices: List<Choice> = emptyList(),
    val error: ErrorData? = null
)

@Serializable
data class Choice(
    val message: OpenAiMessage = OpenAiMessage("", "")
)

@Serializable
data class ErrorData(
    val message: String = ""
)

interface OpenAiService {
    @POST("/v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") token: String,
        @Body request: OpenAiRequest
    ): OpenAiResponse
}

class OpenAiProvider(
    private val service: OpenAiService,
    private val apiKey: String
) : AiProvider {
    override suspend fun chat(
        messages: List<ChatMessage>,
        context: AssistantContext,
        temperature: Float
    ): Result<AiResponse> = try {
        if (apiKey.isEmpty()) {
            return Result.failure(Exception("API key not configured"))
        }

        val contextMessages = buildContextMessages(context)
        val allMessages = (contextMessages + messages).map {
            OpenAiMessage(
                role = if (it.role == MessageRole.USER) "user" else "assistant",
                content = it.content
            )
        }

        val request = OpenAiRequest(
            model = "gpt-3.5-turbo",
            messages = allMessages,
            temperature = temperature
        )

        val response = service.chat("Bearer $apiKey", request)
        
        if (response.error != null) {
            Result.failure(Exception(response.error.message))
        } else if (response.choices.isNotEmpty()) {
            val text = response.choices.first().message.content
            val aiResponse = AiResponse(
                text = text,
                action = parseAction(text),
                shouldSpeak = true
            )
            Result.success(aiResponse)
        } else {
            Result.failure(Exception("Empty response from AI"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun buildContextMessages(context: AssistantContext): List<ChatMessage> {
        val messages = mutableListOf<ChatMessage>()
        if (context.relevantCodexEntries.isNotEmpty()) {
            messages.add(
                ChatMessage(
                    role = MessageRole.ASSISTANT,
                    content = "Relevant knowledge from Codex:\n" +
                            context.relevantCodexEntries.joinToString("\n") { "- ${it.title}: ${it.content}" }
                )
            )
        }
        if (context.relevantMemories.isNotEmpty()) {
            messages.add(
                ChatMessage(
                    role = MessageRole.ASSISTANT,
                    content = "Relevant memories:\n" +
                            context.relevantMemories.joinToString("\n") { "- ${it.content}" }
                )
            )
        }
        return messages
    }

    private fun parseAction(text: String): AssistantAction? {
        return when {
            text.contains("remind", ignoreCase = true) -> AssistantAction.REMINDER_CREATE
            text.contains("note", ignoreCase = true) -> AssistantAction.NOTE_CREATE
            text.contains("search", ignoreCase = true) -> AssistantAction.CODEX_SEARCH
            text.contains("remember", ignoreCase = true) -> AssistantAction.MEMORY_SAVE
            else -> null
        }
    }
}
