package com.friday.ai.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ChatMessage(
    val id: String = "",
    val role: MessageRole = MessageRole.USER,
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isVoice: Boolean = false
) : Parcelable

enum class MessageRole {
    USER,
    ASSISTANT
}

@Serializable
data class AiResponse(
    val text: String = "",
    val action: AssistantAction? = null,
    val actionArguments: Map<String, String> = emptyMap(),
    val shouldSpeak: Boolean = true,
    val confidence: Float = 1.0f
)

enum class AssistantAction {
    REMINDER_CREATE,
    NOTE_CREATE,
    CODEX_SEARCH,
    MEMORY_SAVE,
    MEMORY_UPDATE,
    OPEN_SETTINGS,
    WEB_SEARCH,
    UNKNOWN
}

data class AssistantContext(
    val conversationHistory: List<ChatMessage> = emptyList(),
    val relevantCodexEntries: List<CodexEntry> = emptyList(),
    val relevantMemories: List<Memory> = emptyList(),
    val currentTime: Long = System.currentTimeMillis()
)

enum class AssistantStatus {
    READY,
    LISTENING,
    THINKING,
    SPEAKING,
    EXECUTING,
    ERROR,
    OFFLINE
}
