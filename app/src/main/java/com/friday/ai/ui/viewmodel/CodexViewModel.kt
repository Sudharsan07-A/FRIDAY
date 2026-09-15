package com.friday.ai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friday.ai.data.repository.CodexRepository
import com.friday.ai.domain.model.CodexEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CodexViewModel @Inject constructor(
    private val codexRepository: CodexRepository
) : ViewModel() {
    private val _entries = MutableStateFlow<List<CodexEntry>>(emptyList())
    val entries = _entries.asStateFlow()

    private val _searchResults = MutableStateFlow<List<CodexEntry>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    init {
        loadEntries()
    }

    private fun loadEntries() = viewModelScope.launch {
        codexRepository.getAllEntries().collectLatest { _entries.value = it }
    }

    fun createEntry(entry: CodexEntry) = viewModelScope.launch {
        codexRepository.createEntry(entry)
    }

    fun updateEntry(entry: CodexEntry) = viewModelScope.launch {
        codexRepository.updateEntry(entry)
    }

    fun deleteEntry(entry: CodexEntry) = viewModelScope.launch {
        codexRepository.deleteEntry(entry)
    }

    fun search(query: String) = viewModelScope.launch {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = codexRepository.search(query)
        }
    }
}
