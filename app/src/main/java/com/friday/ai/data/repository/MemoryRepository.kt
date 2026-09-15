package com.friday.ai.data.repository

import com.friday.ai.data.database.dao.MemoryDao
import com.friday.ai.domain.model.Memory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemoryRepository @Inject constructor(
    private val memoryDao: MemoryDao
) {
    suspend fun saveMemory(memory: Memory): Long = memoryDao.insert(memory)
    suspend fun updateMemory(memory: Memory) = memoryDao.update(memory)
    suspend fun deleteMemory(memory: Memory) = memoryDao.delete(memory)
    suspend fun getMemory(id: Long) = memoryDao.getById(id)
    fun getAllMemories(): Flow<List<Memory>> = memoryDao.getAllFlow()
    suspend fun search(query: String) = memoryDao.search(query)
    fun getByType(type: String) = memoryDao.getByType(type)
    suspend fun setEnabled(id: Long, enabled: Boolean) = memoryDao.setEnabled(id, enabled)
    suspend fun deleteAll() = memoryDao.deleteAll()
}
