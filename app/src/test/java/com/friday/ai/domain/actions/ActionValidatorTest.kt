package com.friday.ai.domain.actions

import com.friday.ai.domain.model.AssistantAction
import org.junit.Before
import org.junit.Test

class ActionValidatorTest {
    private lateinit var validator: SafeActionValidator

    @Before
    fun setup() {
        validator = SafeActionValidator()
    }

    @Test
    fun `test reminder create validation`() {
        val args = mapOf("title" to "Test", "time" to "10:00")
        val isValid = validator.validate(AssistantAction.REMINDER_CREATE, args)
        assert(isValid)
    }

    @Test
    fun `test reminder create validation fails without title`() {
        val args = mapOf("time" to "10:00")
        val isValid = validator.validate(AssistantAction.REMINDER_CREATE, args)
        assert(!isValid)
    }

    @Test
    fun `test note create validation`() {
        val args = mapOf("content" to "Test note")
        val isValid = validator.validate(AssistantAction.NOTE_CREATE, args)
        assert(isValid)
    }
}
