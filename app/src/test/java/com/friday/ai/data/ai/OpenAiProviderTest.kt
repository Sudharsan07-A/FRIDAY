package com.friday.ai.data.ai

import com.friday.ai.domain.model.AssistantContext
import com.friday.ai.domain.model.ChatMessage
import com.friday.ai.domain.model.MessageRole
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class OpenAiProviderTest {
    private lateinit var provider: OpenAiProvider
    private val mockService = mockk<OpenAiService>()

    @Before
    fun setup() {
        provider = OpenAiProvider(mockService, "test-key")
    }

    @Test
    fun `test empty api key fails`() = runBlocking {
        provider = OpenAiProvider(mockService, "")
        val messages = listOf(ChatMessage(role = MessageRole.USER, content = "Test"))
        val context = AssistantContext()
        
        val result = provider.chat(messages, context)
        assert(result.isFailure)
    }

    @Test
    fun `test valid response parsing`() = runBlocking {
        val mockResponse = OpenAiResponse(
            choices = listOf(
                Choice(
                    message = OpenAiMessage(role = "assistant", content = "Test response")
                )
            )
        )
        
        coEvery { mockService.chat(any(), any()) } returns mockResponse
        
        val messages = listOf(ChatMessage(role = MessageRole.USER, content = "Test"))
        val context = AssistantContext()
        
        val result = provider.chat(messages, context)
        assert(result.isSuccess)
        assert(result.getOrNull()?.text == "Test response")
    }
}
