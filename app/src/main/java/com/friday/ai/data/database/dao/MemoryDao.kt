package com.friday.ai.data.database.dao

import androidx.room.*
import com.friday.ai.domain.model.Memory
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memory: Memory): Long

    @Update
    suspend fun update(memory: Memory)

    @Delete
    suspend fun delete(memory: Memory)

    @Query("SELECT * FROM memories WHERE id = :id")
    suspend fun getById(id: Long): Memory?

    @Query("SELECT * FROM memories WHERE enabled = 1 ORDER BY updatedAt DESC")
    fun getAllFlow(): Flow<List<Memory>>

    @Query("SELECT * FROM memories WHERE enabled = 1 ORDER BY updatedAt DESC")
    suspend fun getAll(): List<Memory>

    @Query("SELECT * FROM memories WHERE type = :type ORDER BY updatedAt DESC")
    fun getByType(type: String): Flow<List<Memory>>

    @Query("SELECT * FROM memories WHERE content LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    suspend fun search(query: String): List<Memory>

    @Query("UPDATE memories SET enabled = :enabled WHERE id = :id")
    suspend fun setEnabled(id: Long, enabled: Boolean)

    @Query("DELETE FROM memories")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM memories WHERE enabled = 1")
    fun getCount(): Flow<Int>
}
