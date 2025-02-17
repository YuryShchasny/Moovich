package com.sb.moovich.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.sb.moovich.core.extensions.launch
import com.sb.moovich.core.navigation.INavigation
import com.sb.moovich.domain.repository.AuthRepository
import com.sb.moovich.domain.repository.GPTRepository
import com.sb.moovich.domain.repository.ProfileRepository
import com.sb.moovich.presentation.profile.ui.model.ProfileFragmentEvent
import com.sb.moovich.presentation.profile.ui.model.ProfileFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val gptRepository: GPTRepository,
    private val profileRepository: ProfileRepository,
    private val navigation: INavigation
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileFragmentState?>(null)
    val state = _state.asStateFlow()

    init {
        val token = authRepository.getToken()
        launch {
            combine(
                profileRepository.getCacheSize(),
                gptRepository.getHistory()
            ) { cache, history ->
                ProfileFragmentState(
                    token = token,
                    cache = cache,
                    messages = history.size
                )
            }.collect { state ->
                _state.update { state }
            }
        }
    }

    fun fetchEvent(event: ProfileFragmentEvent) {
        when (event) {
            ProfileFragmentEvent.ClearAssistantHistory -> {
                launch { gptRepository.clearHistory() }
            }

            ProfileFragmentEvent.Logout -> {
                launch { authRepository.logout() }
                navigation.navigateToAuth()
            }

            ProfileFragmentEvent.ClearCache -> launch(onError = {
                throw it
            }) { profileRepository.clearCache() }
        }
    }

    fun updateCacheSize() = launch { profileRepository.updateSize() }
}
