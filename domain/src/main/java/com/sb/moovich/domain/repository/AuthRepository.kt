package com.sb.moovich.domain.repository

interface AuthRepository {
    suspend fun login(token: String): Boolean
    suspend fun checkLogin(): Boolean
    suspend fun logout()
    fun getToken(): String
}