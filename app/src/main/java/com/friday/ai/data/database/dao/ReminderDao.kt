package com.friday.ai.data.database.dao

import androidx.room.*
import com.friday.ai.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder): Long

    @Update
    suspend fun update(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getById(id: Long): Reminder?

    @Query("SELECT * FROM reminders WHERE enabled = 1 ORDER BY scheduledAt ASC")
    fun getAllFlow(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders WHERE enabled = 1 ORDER BY scheduledAt ASC")
    suspend fun getAll(): List<Reminder>

    @Query("SELECT * FROM reminders WHERE enabled = 1 AND scheduledAt > :now AND scheduledAt < :laterTime ORDER BY scheduledAt ASC")
    suspend fun getUpcoming(now: Long, laterTime: Long): List<Reminder>

    @Query("SELECT * FROM reminders WHERE enabled = 1 AND scheduledAt <= :now ORDER BY scheduledAt DESC LIMIT 10")
    suspend fun getRecent(now: Long): List<Reminder>

    @Query("DELETE FROM reminders")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM reminders WHERE enabled = 1")
    fun getCount(): Flow<Int>
}
