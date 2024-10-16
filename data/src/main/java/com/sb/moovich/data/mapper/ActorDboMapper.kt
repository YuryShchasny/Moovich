package com.sb.moovich.data.mapper

import com.sb.moovich.data.local.dbo.ActorDbo
import com.sb.moovich.domain.entity.Actor
import javax.inject.Inject

class ActorDboMapper @Inject constructor() : IDataMapper<ActorDbo, Actor> {
    override suspend fun mapDataToEntity(data: ActorDbo): Actor = Actor(
        id = data.id,
        photo = data.photo,
        name = data.name,
        description = data.description
    )

    override suspend fun mapEntityToData(entity: Actor): ActorDbo = ActorDbo(
        id = entity.id,
        photo = entity.photo,
        name = entity.name,
        description = entity.description
    )
}