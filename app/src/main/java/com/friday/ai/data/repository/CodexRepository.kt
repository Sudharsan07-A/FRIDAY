package com.friday.ai.data.repository

import com.friday.ai.data.database.dao.CodexEntryDao
import com.friday.ai.domain.model.CodexEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CodexRepository @Inject constructor(
    private val codexEntryDao: CodexEntryDao
) {
    suspend fun createEntry(entry: CodexEntry): Long = codexEntryDao.insert(entry)
    suspend fun updateEntry(entry: CodexEntry) = codexEntryDao.update(entry)
    suspend fun deleteEntry(entry: CodexEntry) = codexEntryDao.delete(entry)
    suspend fun getEntry(id: Long) = codexEntryDao.getById(id)
    fun getAllEntries(): Flow<List<CodexEntry>> = codexEntryDao.getAllFlow()
    suspend fun search(query: String) = codexEntryDao.search(query)
    fun getByCategory(category: String) = codexEntryDao.getByCategory(category)
    suspend fun setPinned(id: Long, pinned: Boolean) = codexEntryDao.setPinned(id, pinned)
    suspend fun deleteAll() = codexEntryDao.deleteAll()
}
