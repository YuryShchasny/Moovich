package com.sb.moovich.core.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.core.exceptions.ResponseExceptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
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

fun ViewModel.launch(
    errorFlow: MutableSharedFlow<ResponseExceptions>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(
        context = dispatcher + CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch { (throwable as? ResponseExceptions)?.let { errorFlow.emit(it) } }
        },
        block = block
    )
}
