package com.friday.ai.data.database.dao

import androidx.room.*
import com.friday.ai.domain.model.CodexEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface CodexEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: CodexEntry): Long

    @Update
    suspend fun update(entry: CodexEntry)

    @Delete
    suspend fun delete(entry: CodexEntry)

    @Query("SELECT * FROM codex_entries WHERE id = :id")
    suspend fun getById(id: Long): CodexEntry?

    @Query("SELECT * FROM codex_entries ORDER BY pinned DESC, updatedAt DESC")
    fun getAllFlow(): Flow<List<CodexEntry>>

    @Query("SELECT * FROM codex_entries ORDER BY pinned DESC, updatedAt DESC")
    suspend fun getAll(): List<CodexEntry>

    @Query("SELECT * FROM codex_entries WHERE category = :category ORDER BY pinned DESC, updatedAt DESC")
    fun getByCategory(category: String): Flow<List<CodexEntry>>

    @Query("SELECT * FROM codex_entries WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY pinned DESC, updatedAt DESC")
    suspend fun search(query: String): List<CodexEntry>

    @Query("SELECT * FROM codex_entries WHERE tags LIKE '%' || :tag || '%' ORDER BY pinned DESC, updatedAt DESC")
    fun getByTag(tag: String): Flow<List<CodexEntry>>

    @Query("UPDATE codex_entries SET pinned = :pinned WHERE id = :id")
    suspend fun setPinned(id: Long, pinned: Boolean)

    @Query("DELETE FROM codex_entries")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM codex_entries")
    fun getCount(): Flow<Int>
}
