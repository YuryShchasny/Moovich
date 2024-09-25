package com.sb.moovich.core.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.launch(
    onError: suspend (Throwable) -> Unit = {},
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(
        context = dispatcher + CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch { onError(throwable) }
        },
        block = block
    )
}