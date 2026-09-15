package com.friday.ai.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "codex_entries")
@Serializable
data class CodexEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val category: CodexCategory = CodexCategory.KNOWLEDGE,
    val tags: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val pinned: Boolean = false
)

enum class CodexCategory {
    KNOWLEDGE,
    PROJECTS,
    WORK,
    LEARNING,
    IDEAS,
    PERSONAL,
    TASKS
}

fun CodexCategory.displayName(): String = when (this) {
    CodexCategory.KNOWLEDGE -> "Knowledge"
    CodexCategory.PROJECTS -> "Projects"
    CodexCategory.WORK -> "Work"
    CodexCategory.LEARNING -> "Learning"
    CodexCategory.IDEAS -> "Ideas"
    CodexCategory.PERSONAL -> "Personal"
    CodexCategory.TASKS -> "Tasks"
}
