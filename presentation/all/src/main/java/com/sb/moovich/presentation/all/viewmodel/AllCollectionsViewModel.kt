package com.sb.moovich.presentation.all.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.usecases.all.CollectionsNextPageUseCase
import com.sb.moovich.domain.usecases.all.GetAllCollectionsUseCase
import com.sb.moovich.presentation.all.ui.AllCollectionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCollectionsViewModel @Inject constructor(
    private val getAllCollectionsUseCase: GetAllCollectionsUseCase,
    private val collectionsNextPageUseCase: CollectionsNextPageUseCase
): ViewModel() {
    private val _state = MutableStateFlow<AllCollectionsState>(AllCollectionsState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCollectionsUseCase().collect { list ->
                _state.update {
                    if(it is AllCollectionsState.Collections) it.copy(collections = it.collections + list)
                    else AllCollectionsState.Collections(list)
                }
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsNextPageUseCase()
        }
    }
}