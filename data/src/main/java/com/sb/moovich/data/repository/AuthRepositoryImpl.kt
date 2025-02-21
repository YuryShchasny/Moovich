package com.sb.moovich.data.repository

import com.sb.moovich.data.local.datasource.AuthLocalDataSource
import com.sb.moovich.data.remote.datasource.LoginRemoteDataSource
import com.sb.moovich.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val loginRemoteDataSource: LoginRemoteDataSource
) : AuthRepository {

    override suspend fun login(token: String): Boolean {
        authLocalDataSource.setToken(token)
        val result = loginRemoteDataSource.checkToken()
        if (result) authLocalDataSource.saveToken(token)
        return result
    }

    override suspend fun checkLogin(): Boolean {
        return authLocalDataSource.token.isNotEmpty()
    }

    override suspend fun logout() {
        authLocalDataSource.saveToken("")
    }

    override fun getToken(): String = authLocalDataSource.token
}
