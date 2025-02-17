package com.sb.moovich.data.remote.pagination

import com.sb.moovich.core.exceptions.ResponseExceptions
import com.sb.moovich.data.mapper.IDataMapper
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class Paginator<Dto, Entity>(
    request: suspend (Int) -> List<Dto>?,
    mapper: IDataMapper<Dto, Entity>
) : IPaginator<Entity> {

    private var page =
        MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    private var finalPage = false

    override val data = flow {
        page.collect { page ->
            if (finalPage) return@collect
            while(true) {
                try {
                    val response = request(page)
                    if (response.isNullOrEmpty()) finalPage = true
                    response?.let {
                        val entityList = it.map { entity ->
                            mapper.mapDataToEntity(entity)
                        }
                        emit(entityList)
                    }
                    break
                } catch (e: Exception) {
                    if(e is ResponseExceptions) throw e
                    else delay(5000)
                }
            }
        }
    }.onStart {
        page.emit(1)
    }

    override suspend fun nextPage() {
        if (!finalPage) page.emit(page.replayCache.first() + 1)
    }
}