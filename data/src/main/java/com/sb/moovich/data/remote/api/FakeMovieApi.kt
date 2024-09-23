package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.BackdropDto
import com.sb.moovich.data.remote.dto.CollectionDocDto
import com.sb.moovich.data.remote.dto.CollectionDto
import com.sb.moovich.data.remote.dto.CoverDto
import com.sb.moovich.data.remote.dto.GenreDto
import com.sb.moovich.data.remote.dto.MovieDocDto
import com.sb.moovich.data.remote.dto.MovieDto
import com.sb.moovich.data.remote.dto.MoviePosterDto
import com.sb.moovich.data.remote.dto.MovieRatingDto
import com.sb.moovich.data.remote.dto.PersonDto
import retrofit2.Response

class FakeMovieApi : MovieApi {

    private val movies = listOf(
        MovieDto(
            263531,
            "Мстители",
            "The Avengers",
            "Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.",
            MovieRatingDto(8.1, 5.6),
            MoviePosterDto(
                "https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000",
                "https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000"
            ),
            BackdropDto("https://image.openmoviedb.com/kinopoisk-ott-images/374297/2a00000168e7cbf1bf723879ea306ad8f363/orig"),
            137,
            "movie",
            2012,
            listOf(GenreDto("фантастика"), GenreDto("боевик"), GenreDto("фэнтэзи")),
            listOf(
                PersonDto(
                    10096,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_10096.jpg",
                    "Роберт Дауни мл.",
                    "Tony Stark / Iron Man",
                    "actor"
                ),
                PersonDto(
                    49627,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_49627.jpg",
                    "Крис Эванс",
                    "Steve Rogers / Captain America",
                    "actor"
                ),
                PersonDto(
                    10467,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_10467.jpg",
                    "Марк Руффало",
                    "Bruce Banner / The Hulk",
                    "actor"
                ),
                PersonDto(
                    1300401,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_1300401.jpg",
                    "Крис Хемсворт",
                    "Thor",
                    "actor"
                ),
                PersonDto(
                    3903,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_3903.jpg",
                    "Скарлетт Йоханссон",
                    "Natasha Romanoff / Black Widow",
                    "actor"
                ),
                PersonDto(
                    5468,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_5468.jpg",
                    "Джереми Реннер",
                    "Clint Barton / Hawkeye",
                    "actor"
                ),
                PersonDto(
                    553143,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_553143.jpg",
                    "Том Хиддлстон",
                    "Loki",
                    "actor"
                ),
                PersonDto(
                    7164,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_7164.jpg",
                    "Сэмюэл Л. Джексон",
                    "Nick Fury",
                    "actor"
                ),
                PersonDto(
                    11536,
                    "https://image.openmoviedb.com/kinopoisk-st-images//actor_iphone/iphone360_11536.jpg",
                    "Кларк Грегг",
                    "Agent Phil Coulson",
                    "actor"
                ),
            ),
            null
        ),
        MovieDto(
            462358,
            "Люди Икс: Первый класс",
            "X-Men: First Class",
            null,
            MovieRatingDto(7.6443, 7.7),
            MoviePosterDto(
                "https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000",
                "https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"
            ),
            null,
            null,
            "movie",
            2011,
            null,
            null,
            null
        ),
        MovieDto(
            437410,
            "Темный рыцарь: Возрождение легенды",
            "The Dark Knight Rises",
            null,
            MovieRatingDto(8.165, 8.4),
            MoviePosterDto(
                "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000",
                "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"
            ),
            null,
            null,
            "movie",
            2012,
            null,
            null,
            null
        ),
        MovieDto(
            452899,
            "Трансформеры 3: Тёмная сторона Луны",
            "Transformers: Dark of the Moon",
            null,
            MovieRatingDto(6.848, 6.2),
            MoviePosterDto(
                "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000",
                "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"
            ),
            null,
            null,
            "movie",
            2011,
            null,
            null,
            null
        ),
    )

    private val collections = listOf(
        CollectionDto(
            "Озвучено студией «Кубик в кубе", "Озвучено студией «Кубик в кубе", 25, CoverDto(
                "https://avatars.mds.yandex.net/get-bunker/56833/4a2969f3525706ce01799173e78f2716b0566b28/orig",
                "https://avatars.mds.yandex.net/get-bunker/56833/4a2969f3525706ce01799173e78f2716b0566b28/orig"
            )
        ),
        CollectionDto(
            "Озвучено студией «Кубик в кубе", "Фильмы, которые стоит посмотреть", 220, CoverDto(
                "https://avatars.mds.yandex.net/get-bunker/50064/9a7c29bf643a8f27c2f95502bf604b1e2a3a8474/orig",
                "https://avatars.mds.yandex.net/get-bunker/50064/9a7c29bf643a8f27c2f95502bf604b1e2a3a8474/orig"
            )
        ),
        CollectionDto(
            "Озвучено студией «Кубик в кубе", "Пересматриваем любимое", 10, CoverDto(
                "https://avatars.mds.yandex.net/get-bunker/56833/68de79bf6f0b4b3b299688620cb87939c0962a6f/orig",
                "https://avatars.mds.yandex.net/get-bunker/56833/68de79bf6f0b4b3b299688620cb87939c0962a6f/orig"
            )
        ),
    )

    override suspend fun getTop5Movies(
        page: Int,
        limit: Int,
        sortField: String,
        sortType: String,
        year: String,
        rating: String,
        selectFields: List<String>,
    ): Response<MovieDocDto> {
        return Response.success(
            MovieDocDto(
                (movies + movies).take(5)
            )
        )
    }

    override suspend fun getTop10Series(
        page: Int,
        limit: Int,
        sortField: String,
        sortType: String,
        collection: String,
        selectFields: List<String>
    ): Response<MovieDocDto> {
        return Response.success(
            MovieDocDto((movies + movies + movies).take(10))
        )
    }

    override suspend fun getCollections(
        page: Int,
        limit: Int,
        category: String?,
        selectFields: List<String>,
    ): Response<CollectionDocDto> {
        return Response.success(
            CollectionDocDto(
                collections + collections
            )
        )
    }

    override suspend fun getTop10MonthMovies(
        page: Int,
        limit: Int,
        lists: String,
        selectFields: List<String>,
    ): Response<MovieDocDto> {
        return Response.success(
            MovieDocDto(
                (movies + movies + movies).take(10)
            )
        )
    }

    override suspend fun getRecommendedMovies(
        page: Int,
        limit: Int,
        sortField: String,
        sortType: String,
        year: String,
        rating: String,
        selectFields: List<String>,
    ): Response<MovieDocDto> {
        return Response.success(
            MovieDocDto(
                movies + movies + movies
            )
        )
    }

    override suspend fun getMovieById(
        id: Int,
        selectFields: List<String>,
    ): Response<MovieDto> {
        return Response.success(
            movies.first().copy(similarMovies = movies)
        )
    }

    override suspend fun findMovie(
        page: Int,
        limit: Int,
        name: String,
        selectFields: List<String>,
    ): Response<MovieDocDto> {
        return Response.success(
            MovieDocDto(
                movies.filter { it.name?.contains(name, ignoreCase = true) ?: false }
            )
        )
    }

    override suspend fun filterMovie(
        page: Int,
        limit: Int,
        sortField: String,
        sortType: String,
        type: String?,
        year: String,
        rating: String,
        genres: Array<String>,
        countries: Array<String>,
        selectFields: List<String>,
    ): Response<MovieDocDto> {
        return findMovie(0, 0, "")
    }

    override suspend fun getMoviesByGenre(
        genre: String,
        page: Int,
        limit: Int,
        sortField: String,
        sortType: String,
        selectFields: List<String>
    ): Response<MovieDocDto> {
        return Response.success(
            MovieDocDto((movies + movies + movies).take(10))
        )
    }

    override suspend fun getMoviesByCollection(
        collection: String,
        page: Int,
        limit: Int,
        selectFields: List<String>
    ): Response<MovieDocDto> {
        return Response.success(
            MovieDocDto((movies + movies + movies).take(10))
        )
    }
}