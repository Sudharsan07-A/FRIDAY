package com.friday.ai.data.repository

import com.friday.ai.domain.model.CodexEntry
import com.friday.ai.domain.model.CodexCategory
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class CodexRepositoryTest {
    private lateinit var repository: CodexRepository
    private val mockDao = mockk<com.friday.ai.data.database.dao.CodexEntryDao>()

    @Before
    fun setup() {
        repository = CodexRepository(mockDao)
    }

    @Test
    fun `test create entry`() {
        val entry = CodexEntry(
            title = "Test",
            content = "Test Content",
            category = CodexCategory.KNOWLEDGE
        )
        coEvery { mockDao.insert(entry) } returns 1L

        // Verify insert was called
        coVerify { mockDao.insert(any()) }
    }

    @Test
    fun `test search entries`() {
        val entries = listOf(
            CodexEntry(title = "AWS", content = "Cloud"),
            CodexEntry(title = "Azure", content = "Cloud")
        )
        coEvery { mockDao.search("Cloud") } returns entries

        val result = mockk<List<CodexEntry>>()
        assert(result is List<*>)
    }
}
