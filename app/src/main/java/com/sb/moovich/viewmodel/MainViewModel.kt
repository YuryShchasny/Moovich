package com.sb.moovich.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        launch {
            _isLoggedIn.update { authRepository.checkLogin() }
        }
    }
}
