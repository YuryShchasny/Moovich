package com.sb.moovich.presentation.authorization.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.exceptions.ResponseExceptions
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.domain.repository.AuthRepository
import com.sb.moovich.presentation.authorization.ui.model.AuthorizationFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthorizationFragmentState())
    val state = _state.asStateFlow()

    private val _error = MutableSharedFlow<ResponseExceptions>()
    val error = _error.asSharedFlow()

    fun login(token: String) {
        launch(_error) {
            _state.update {
                it.copy(isAuthorized = repository.login(token))
            }
        }
    }
}
