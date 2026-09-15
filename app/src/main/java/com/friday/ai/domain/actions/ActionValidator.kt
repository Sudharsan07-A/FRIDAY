package com.friday.ai.domain.actions

import com.friday.ai.domain.model.AssistantAction

interface ActionValidator {
    fun validate(action: AssistantAction, arguments: Map<String, String>): Boolean
}

class SafeActionValidator : ActionValidator {
    override fun validate(action: AssistantAction, arguments: Map<String, String>): Boolean {
        return when (action) {
            AssistantAction.REMINDER_CREATE -> arguments.containsKey("title") && arguments.containsKey("time")
            AssistantAction.NOTE_CREATE -> arguments.containsKey("content")
            AssistantAction.CODEX_SEARCH -> arguments.containsKey("query")
            AssistantAction.MEMORY_SAVE -> arguments.containsKey("content")
            AssistantAction.MEMORY_UPDATE -> arguments.containsKey("id") && arguments.containsKey("content")
            AssistantAction.OPEN_SETTINGS -> true
            AssistantAction.WEB_SEARCH -> arguments.containsKey("query")
            AssistantAction.UNKNOWN -> false
        }
    }
}
