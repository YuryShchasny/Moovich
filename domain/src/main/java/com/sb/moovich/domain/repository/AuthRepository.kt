package com.sb.moovich.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(token: String)
    suspend fun checkLogin(): Flow<Boolean>
}