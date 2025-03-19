package com.example.lab4.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val namesList = listOf("Eslam", "Elsayed", "Ahmed", "Mohamed", "Ali", "Hossam", "Adam", "Khalid")

    private val _searchQuery = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _filteredNames = MutableStateFlow<List<String>>(emptyList())
    val filteredNames: StateFlow<List<String>> = _filteredNames.asStateFlow()

    init {
        observeSearchQuery()
    }

    fun search(query: String) {
        viewModelScope.launch {
            _searchQuery.emit(query)
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .collect { query ->
                    _filteredNames.value = if (query.isNotBlank()) {
                        namesList.filter { it.startsWith(query, ignoreCase = true) }
                    } else {
                        emptyList()
                    }
                }
        }
    }
}
