package com.sb.moovich.core.mapper

import com.sb.moovich.core.adapters.mediummovies.MediumMovieInfo
import com.sb.moovich.domain.entity.Movie

class MovieToUIMapper {
    fun mapToMediumMovie(movie: Movie): MediumMovieInfo {
        return MediumMovieInfo(
            id = movie.id,
            name = movie.name,
            description = movie.description,
            rating = movie.rating,
            poster = movie.poster,
            year = movie.year,
            movieLength = movie.movieLength,
            genres = movie.genres
        )
    }
}