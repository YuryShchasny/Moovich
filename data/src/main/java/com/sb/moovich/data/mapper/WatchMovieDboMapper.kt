package com.sb.moovich.data.mapper

import com.sb.moovich.data.local.dao.ActorDao
import com.sb.moovich.data.local.dbo.WatchMovieDbo
import com.sb.moovich.data.remote.dto.MovieDto
import com.sb.moovich.domain.entity.Movie
import javax.inject.Inject

class WatchMovieDboMapper @Inject constructor(
    private val actorDao: ActorDao,
    private val actorDboMapper: ActorDboMapper
) : IDataMapper<WatchMovieDbo, Movie> {
    override suspend fun mapDataToEntity(data: WatchMovieDbo) = Movie(
        id = data.id,
        name = data.name,
        description = data.description,
        rating = data.rating,
        poster = data.poster,
        backdrop = data.backdrop,
        movieLength = data.movieLength,
        urlWatch = data.urlWatch,
        year = data.year,
        genres = data.genres,
        actors = data.actors.mapNotNull {
            actorDao.getActorById(it)?.let { actor ->
                actorDboMapper.mapDataToEntity(
                    actor
                )
            }
        },
        similarMovies = emptyList()
    )

    override suspend fun mapEntityToData(entity: Movie) = WatchMovieDbo(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        rating = entity.rating,
        poster = entity.poster,
        backdrop = entity.backdrop,
        movieLength = entity.movieLength,
        urlWatch = entity.urlWatch,
        year = entity.year,
        genres = entity.genres,
        actors = entity.actors.map {
            actorDao.insertActor(actorDboMapper.mapEntityToData(it))
            it.id
        }
    )
}
