package com.friday.ai.data.repository

import com.friday.ai.data.database.dao.ReminderDao
import com.friday.ai.domain.model.Reminder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao
) {
    suspend fun createReminder(reminder: Reminder): Long = reminderDao.insert(reminder)
    suspend fun updateReminder(reminder: Reminder) = reminderDao.update(reminder)
    suspend fun deleteReminder(reminder: Reminder) = reminderDao.delete(reminder)
    suspend fun getReminder(id: Long) = reminderDao.getById(id)
    fun getAllReminders(): Flow<List<Reminder>> = reminderDao.getAllFlow()
    suspend fun getUpcoming(now: Long = System.currentTimeMillis()): List<Reminder> {
        val laterTime = now + (24 * 60 * 60 * 1000) // 24 hours
        return reminderDao.getUpcoming(now, laterTime)
    }
    suspend fun deleteAll() = reminderDao.deleteAll()
}
