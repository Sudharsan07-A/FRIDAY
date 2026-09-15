package com.friday.ai.domain.ai

import com.friday.ai.domain.model.AiResponse
import com.friday.ai.domain.model.AssistantContext
import com.friday.ai.domain.model.ChatMessage
import com.friday.ai.domain.model.MessageRole
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AiBrainTest {
    private lateinit var aiBrain: AiBrain
    private val mockAiProvider = mockk<AiProvider>()
    private val mockCodexRepository = mockk<com.friday.ai.data.repository.CodexRepository>()
    private val mockMemoryRepository = mockk<com.friday.ai.data.repository.MemoryRepository>()
    private val mockConversationRepository = mockk<com.friday.ai.data.repository.ConversationRepository>()

    @Before
    fun setup() {
        aiBrain = AiBrain(
            mockAiProvider,
            mockCodexRepository,
            mockMemoryRepository,
            mockConversationRepository
        )
    }

    @Test
    fun `test process message`() = runBlocking {
        val message = "Hello FRIDAY"
        val mockResponse = AiResponse(text = "Hello user")
        
        coEvery { mockConversationRepository.getMessages(any()) } returns kotlinx.coroutines.flow.flowOf(emptyList())
        coEvery { mockCodexRepository.search(any()) } returns emptyList()
        coEvery { mockMemoryRepository.getMemory(any()) } returns null
        coEvery { mockAiProvider.chat(any(), any(), any()) } returns Result.success(mockResponse)

        val result = aiBrain.process(message, 1L)
        assert(result.isSuccess)
    }
}
