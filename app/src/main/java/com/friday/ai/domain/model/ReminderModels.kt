package com.friday.ai.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "reminders")
@Serializable
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val scheduledAt: Long = 0,
    val repeatRule: RepeatRule = RepeatRule.ONCE,
    val enabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

enum class RepeatRule {
    ONCE,
    DAILY,
    WEEKLY
}

fun RepeatRule.displayName(): String = when (this) {
    RepeatRule.ONCE -> "Once"
    RepeatRule.DAILY -> "Daily"
    RepeatRule.WEEKLY -> "Weekly"
}
