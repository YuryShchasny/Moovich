package com.sb.moovich.domain.usecases.auth

import com.sb.moovich.domain.repository.AuthRepository
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = repository.checkLogin()
}