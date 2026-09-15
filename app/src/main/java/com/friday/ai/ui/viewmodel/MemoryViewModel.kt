package com.friday.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friday.ai.data.repository.MemoryRepository
import com.friday.ai.domain.model.Memory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MemoryViewModel @Inject constructor(
    private val memoryRepository: MemoryRepository
) : ViewModel() {
    private val _memories = MutableStateFlow<List<Memory>>(emptyList())
    val memories = _memories.asStateFlow()

    init {
        loadMemories()
    }

    private fun loadMemories() = viewModelScope.launch {
        memoryRepository.getAllMemories().collectLatest { _memories.value = it }
    }

    fun saveMemory(memory: Memory) = viewModelScope.launch {
        memoryRepository.saveMemory(memory)
    }

    fun updateMemory(memory: Memory) = viewModelScope.launch {
        memoryRepository.updateMemory(memory)
    }

    fun deleteMemory(memory: Memory) = viewModelScope.launch {
        memoryRepository.deleteMemory(memory)
    }
}
