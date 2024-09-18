package com.sb.moovich.data.remote.api

import com.sb.moovich.data.remote.dto.BackdropDto
import com.sb.moovich.data.remote.dto.GenreDto
import com.sb.moovich.data.remote.dto.MovieDocDto
import com.sb.moovich.data.remote.dto.MovieDto
import com.sb.moovich.data.remote.dto.MoviePosterDto
import com.sb.moovich.data.remote.dto.MovieRatingDto
import com.sb.moovich.data.remote.dto.PersonDto
import kotlinx.coroutines.delay
import retrofit2.Response

class FakeMovieApi : MovieApi {
    override suspend fun getRecommendedMovies(
        apiKey: String,
        page: Int,
        limit: Int,
        sortField: String,
        sortType: String,
        releaseYearEnd: String,
        rating: String
    ): Response<MovieDocDto> {
        delay(1000)
        return Response.success(
            MovieDocDto(
                listOf(
                    MovieDto(
                        263531,
                        "Мстители",
                        "The Avengers",
                        "Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.",
                        MovieRatingDto(8.1, 5.6),
                        MoviePosterDto("https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000"),
                        BackdropDto("https://image.openmoviedb.com/kinopoisk-ott-images/374297/2a00000168e7cbf1bf723879ea306ad8f363/orig"),
                        137,
                        "movie",
                        2012,
                        listOf(GenreDto("фантастика"), GenreDto("боевик"), GenreDto("фэнтэзи")),
                        null,
                        null
                    ),
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    ),
                    MovieDto(
                        263531,
                        "Мстители",
                        "The Avengers",
                        "Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.",
                        MovieRatingDto(8.1, 5.6),
                        MoviePosterDto("https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000"),
                        BackdropDto("https://image.openmoviedb.com/kinopoisk-ott-images/374297/2a00000168e7cbf1bf723879ea306ad8f363/orig"),
                        137,
                        "movie",
                        2012,
                        listOf(GenreDto("фантастика"), GenreDto("боевик"), GenreDto("фэнтэзи")),
                        null,
                        null
                    ),
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    ),
                    MovieDto(
                        263531,
                        "Мстители",
                        "The Avengers",
                        "Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.",
                        MovieRatingDto(8.1, 5.6),
                        MoviePosterDto("https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000"),
                        BackdropDto("https://image.openmoviedb.com/kinopoisk-ott-images/374297/2a00000168e7cbf1bf723879ea306ad8f363/orig"),
                        137,
                        "movie",
                        2012,
                        listOf(GenreDto("фантастика"), GenreDto("боевик"), GenreDto("фэнтэзи")),
                        null,
                        null
                    ),
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    ),
                    MovieDto(
                        263531,
                        "Мстители",
                        "The Avengers",
                        "Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.",
                        MovieRatingDto(8.1, 5.6),
                        MoviePosterDto("https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000"),
                        BackdropDto("https://image.openmoviedb.com/kinopoisk-ott-images/374297/2a00000168e7cbf1bf723879ea306ad8f363/orig"),
                        137,
                        "movie",
                        2012,
                        listOf(GenreDto("фантастика"), GenreDto("боевик"), GenreDto("фэнтэзи")),
                        null,
                        null
                    ),
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    ),
                )
            )
        )
    }

    override suspend fun getMovieById(id: Int, apiKey: String): Response<MovieDto> {
        delay(1000)
        return Response.success(
            MovieDto(
                263531,
                "Мстители",
                "The Avengers",
                "Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.",
                MovieRatingDto(8.1, 5.6),
                MoviePosterDto("https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000"),
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
                listOf(
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    ),
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    ),
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    )
                )
            )
        )
    }

    override suspend fun findMovie(
        apiKey: String,
        page: Int,
        limit: Int,
        name: String
    ): Response<MovieDocDto> {
        delay(1000)
        return Response.success(
            MovieDocDto(
                listOf(
                    MovieDto(
                        263531,
                        "Мстители",
                        "The Avengers",
                        "Локи, сводный брат Тора, возвращается, и в этот раз он не один. Земля оказывается на грани порабощения, и только лучшие из лучших могут спасти человечество. Глава международной организации Щ.И.Т. Ник Фьюри собирает выдающихся поборников справедливости и добра, чтобы отразить атаку. Под предводительством Капитана Америки Железный Человек, Тор, Невероятный Халк, Соколиный Глаз и Чёрная Вдова вступают в войну с захватчиком.",
                        MovieRatingDto(8.1, 5.6),
                        MoviePosterDto("https://image.openmoviedb.com/kinopoisk-images/1898899/972b7f43-9677-40ce-a9bc-02a88ad3919d/x1000"),
                        BackdropDto("https://image.openmoviedb.com/kinopoisk-ott-images/374297/2a00000168e7cbf1bf723879ea306ad8f363/orig"),
                        137,
                        "movie",
                        2012,
                        listOf(GenreDto("фантастика"), GenreDto("боевик"), GenreDto("фэнтэзи")),
                        null,
                        null
                    ),
                    MovieDto(
                        462358,
                        "Люди Икс: Первый класс",
                        "X-Men: First Class",
                        null,
                        MovieRatingDto(7.6443, 7.7),
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/851c15e3-2729-45bc-b3e7-ea684728c725/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78a50ddd-1712-4bb0-869e-680963c1580e/x1000"),
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
                        MoviePosterDto("https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/1378a1f6-fcfa-4bf7-b8d9-e48ba74ef2c8/x1000"),
                        null,
                        null,
                        "movie",
                        2011,
                        null,
                        null,
                        null
                    )
                ).filter { it.name?.contains(name, ignoreCase = true) ?: false}
            )
        )
    }

    override suspend fun filterMovie(
        apiKey: String,
        page: Int,
        limit: Int,
        type: String?,
        year: String,
        rating: String,
        genres: Array<String>,
        countries: Array<String>
    ): Response<MovieDocDto> {
        delay(1000)
        return findMovie("", 0, 0, "")
    }
}