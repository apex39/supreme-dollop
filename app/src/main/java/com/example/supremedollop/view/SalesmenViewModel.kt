
package com.example.supremedollop.view

import androidx.lifecycle.ViewModel
import com.example.supremedollop.repository.SalesmanRepository
import androidx.lifecycle.*
import com.example.supremedollop.model.Salesman
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SalesmanViewModel @Inject constructor(
    private val repository: SalesmanRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")

    private val _salesmenResults = MutableStateFlow<List<Salesman>>(emptyList())
    val salesmenResults: StateFlow<List<Salesman>> = _salesmenResults.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .filter { query -> query.isNotBlank() }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow { emit(repository.findSalesmenByArea(query)) }
                }
                .collect { results ->
                    _salesmenResults.value = results
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}