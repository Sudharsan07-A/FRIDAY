package com.friday.ai.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "memories")
@Serializable
data class Memory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: MemoryType = MemoryType.PREFERENCE,
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val enabled: Boolean = true
)

enum class MemoryType {
    PREFERENCE,
    PROJECT_CONTEXT,
    IMPORTANT_FACT,
    INSTRUCTION
}

fun MemoryType.displayName(): String = when (this) {
    MemoryType.PREFERENCE -> "Preference"
    MemoryType.PROJECT_CONTEXT -> "Project Context"
    MemoryType.IMPORTANT_FACT -> "Important Fact"
    MemoryType.INSTRUCTION -> "Instruction"
}
