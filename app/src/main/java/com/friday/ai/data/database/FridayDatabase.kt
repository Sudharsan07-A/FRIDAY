package com.friday.ai.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.friday.ai.data.database.dao.CodexEntryDao
import com.friday.ai.data.database.dao.ConversationDao
import com.friday.ai.data.database.dao.MemoryDao
import com.friday.ai.data.database.dao.ReminderDao
import com.friday.ai.domain.model.CodexEntry
import com.friday.ai.domain.model.Conversation
import com.friday.ai.domain.model.ConversationMessage
import com.friday.ai.domain.model.Memory
import com.friday.ai.domain.model.Reminder

@Database(
    entities = [
        CodexEntry::class,
        Memory::class,
        Reminder::class,
        Conversation::class,
        ConversationMessage::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FridayDatabase : RoomDatabase() {
    abstract fun codexEntryDao(): CodexEntryDao
    abstract fun memoryDao(): MemoryDao
    abstract fun reminderDao(): ReminderDao
    abstract fun conversationDao(): ConversationDao
}
