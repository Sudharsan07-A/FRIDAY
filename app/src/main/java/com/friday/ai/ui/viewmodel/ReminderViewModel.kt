package com.friday.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friday.ai.data.repository.ReminderRepository
import com.friday.ai.domain.model.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {
    private val _reminders = MutableStateFlow<List<Reminder>>(emptyList())
    val reminders = _reminders.asStateFlow()

    init {
        loadReminders()
    }

    private fun loadReminders() = viewModelScope.launch {
        reminderRepository.getAllReminders().collectLatest { _reminders.value = it }
    }

    fun createReminder(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.createReminder(reminder)
    }

    fun updateReminder(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.updateReminder(reminder)
    }

    fun deleteReminder(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.deleteReminder(reminder)
    }
}
