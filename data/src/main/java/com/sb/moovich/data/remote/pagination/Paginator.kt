package com.sb.moovich.data.remote.pagination

import com.sb.moovich.data.mapper.IDataMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class Paginator<Dto, Entity>(
    request: suspend (Int) -> List<Dto>?,
    mapper: IDataMapper<Dto, Entity>
): IPaginator<Entity> {

    private var page = MutableStateFlow(1)

    private var finalPage = false

    override val data = flow {
        page.collect { page ->
            if(finalPage) return@collect
            val response = request(page)
            response?.let {
                if(response.isEmpty()) finalPage = true
                val entityList = it.map { entity ->
                    mapper.mapDataToEntity(entity)
                }
                emit(entityList)
            }
        }
    }

    override fun nextPage() {
        if(!finalPage) page.update { it + 1 }
    }
}