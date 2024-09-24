package com.sb.moovich.data.repository

import com.sb.moovich.data.local.datasource.AuthLocalDataSource
import com.sb.moovich.data.remote.datasource.LoginRemoteDataSource
import com.sb.moovich.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val loginRemoteDataSource: LoginRemoteDataSource
) : AuthRepository {

    private val token = MutableStateFlow<String?>(null)

    init {
        token.update { "" }
        CoroutineScope(Dispatchers.IO).launch {
            authLocalDataSource.subscribeToken().collect {
                val checkResult = loginRemoteDataSource.checkToken()
                if (checkResult) {
                    token.update { it }
                }
            }
        }
    }

    override suspend fun login(token: String) {
        authLocalDataSource.setToken(token)
    }

    override suspend fun checkLogin(): Flow<Boolean> = flow {
        token.collect {
            it?.let {
                emit(it.isNotEmpty())
            }
        }
    }
}
