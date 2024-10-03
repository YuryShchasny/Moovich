package com.sb.moovich.domain.repository

import com.sb.moovich.domain.entity.DataResult

interface AuthRepository {
    suspend fun login(token: String): DataResult
    suspend fun checkLogin(): Boolean
    suspend fun logout()
    fun getToken(): String
}