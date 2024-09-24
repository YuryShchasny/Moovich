package com.sb.moovich.presentation.authorization.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sb.moovich.domain.usecases.auth.CheckLoginUseCase
import com.sb.moovich.domain.usecases.auth.LoginUseCase
import com.sb.moovich.presentation.authorization.ui.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkLoginUseCase: CheckLoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            checkLoginUseCase().collect {
                _state.update { it }
            }
        }
    }

    fun login(token: String) {
        viewModelScope.launch {
            loginUseCase(token)
        }
    }
}