package com.sb.moovich.presentation.authorization.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.domain.entity.DataResult
import com.sb.moovich.domain.entity.ErrorType
import com.sb.moovich.domain.usecases.auth.LoginUseCase
import com.sb.moovich.presentation.authorization.ui.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _error = MutableSharedFlow<DataResult.Error>()
    val error = _error.asSharedFlow()

    fun login(token: String) {
        launch(onError = { _error.emit(DataResult.Error(ErrorType.Another)) }) {
            when (val result = loginUseCase(token)) {
                is DataResult.Error -> _error.emit(result)
                DataResult.Success -> _state.update { it.copy(isAuthorized = true) }
            }
        }
    }
}