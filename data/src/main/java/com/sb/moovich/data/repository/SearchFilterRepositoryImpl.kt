package com.sb.moovich.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.sb.moovich.domain.entity.Filter
import com.sb.moovich.domain.repository.SearchFilterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchFilterRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): SearchFilterRepository {
    private val gson = Gson()

    override suspend fun saveFilter(filter: Filter) {
        val json = gson.toJson(filter)
        sharedPreferences.edit().putString(ARG_FILTER, json).apply()
    }

    override suspend fun getGenres(): List<String> = GENRES

    override suspend fun getCountries(): List<String> = COUNTRIES

     override suspend fun getFilter(): Filter {
        val filter = sharedPreferences.getString(ARG_FILTER, null)
        return filter?.let {
            gson.fromJson(filter, Filter::class.java)
        } ?: Filter()
    }

    companion object {
        private const val ARG_FILTER = "arg_filter"
        private val GENRES = listOf(
            "Аниме",
            "Биография",
            "Боевик",
            "Вестерн",
            "Военный",
            "Детектив",
            "Детский",
            "Документальный",
            "Драма",
            "История",
            "Комедия",
            "Короткометражка",
            "Криминал",
            "Мелодрама",
            "Мюзикл",
            "Приключения",
            "Семейный",
            "Спорт",
            "Ток-шоу",
            "Триллер",
            "Ужасы",
            "Фантастика",
            "Фэнтези",
        )
        private val COUNTRIES = listOf(
            "Австралия",
            "Австрия",
            "Азербайджан",
            "Албания",
            "Алжир",
            "Американские Виргинские острова",
            "Американское Самоа",
            "Ангола",
            "Андорра",
            "Антарктида",
            "Антигуа и Барбуда",
            "Антильские Острова",
            "Аргентина",
            "Армения",
            "Аруба",
            "Афганистан",
            "Багамы",
            "Бангладеш",
            "Барбадос",
            "Бахрейн",
            "Беларусь",
            "Белиз",
            "Бельгия",
            "Бенин",
            "Берег Слоновой кости",
            "Бермуды",
            "Бирма",
            "Болгария",
            "Боливия",
            "Босния",
            "Босния и Герцеговина",
            "Ботсвана",
            "Бразилия",
            "Бруней-Даруссалам",
            "Буркина-Фасо",
            "Бурунди",
            "Бутан",
            "Вануату",
            "Ватикан",
            "Великобритания",
            "Венгрия",
            "Венесуэла",
            "Виргинские Острова",
            "Внешние малые острова США",
            "Вьетнам",
            "Вьетнам Северный",
            "Габон",
            "Гаити",
            "Гайана",
            "Гамбия",
            "Гана",
            "Гваделупа",
            "Гватемала",
            "Гвинея",
            "Гвинея-Бисау",
            "Германия",
            "Германия (ГДР)",
            "Германия (ФРГ)",
            "Гибралтар",
            "Гондурас",
            "Гонконг",
            "Гренада",
            "Гренландия",
            "Греция",
            "Грузия",
            "Гуам",
            "Дания",
            "Джибути",
            "Доминика",
            "Доминикана",
            "Египет",
            "Заир",
            "Замбия",
            "Западная Сахара",
            "Зимбабве",
            "Израиль",
            "Индия",
            "Индонезия",
            "Иордания",
            "Ирак",
            "Иран",
            "Ирландия",
            "Исландия",
            "Испания",
            "Италия",
            "Йемен",
            "Кабо-Верде",
            "Казахстан",
            "Каймановы острова",
            "Камбоджа",
            "Камерун",
            "Канада",
            "Катар",
            "Кения",
            "Кипр",
            "Кирибати",
            "Китай",
            "Колумбия",
            "Коморы",
            "Конго",
            "Конго (ДРК)",
            "Корея",
            "Корея Северная",
            "Корея Южная",
            "Косово",
            "Коста-Рика",
            "Кот-д’Ивуар",
            "Куба",
            "Кувейт",
            "Кыргызстан",
            "Лаос",
            "Латвия",
            "Лесото",
            "Либерия",
            "Ливан",
            "Ливия",
            "Литва",
            "Лихтенштейн",
            "Люксембург",
            "Маврикий",
            "Мавритания",
            "Мадагаскар",
            "Макао",
            "Македония",
            "Малави",
            "Малайзия",
            "Мали",
            "Мальдивы",
            "Мальта",
            "Марокко",
            "Мартиника",
            "Маршалловы острова",
            "Мексика",
            "Мозамбик",
            "Молдова",
            "Монако",
            "Монголия",
            "Монтсеррат",
            "Мьянма",
            "Намибия",
            "Непал",
            "Нигер",
            "Нигерия",
            "Нидерланды",
            "Никарагуа",
            "Новая Зеландия",
            "Новая Каледония",
            "Норвегия",
            "ОАЭ",
            "Оккупированная Палестинская территория",
            "Оман",
            "Остров Мэн",
            "Острова Кука",
            "Пакистан",
            "Палау",
            "Палестина",
            "Панама",
            "Папуа - Новая Гвинея",
            "Парагвай",
            "Перу",
            "Польша",
            "Португалия",
            "Пуэрто Рико",
            "Реюньон",
            "Российская империя",
            "Россия",
            "Руанда",
            "Румыния",
            "СССР",
            "США",
            "Сальвадор",
            "Самоа",
            "Сан-Марино",
            "Саудовская Аравия",
            "Свазиленд",
            "Северная Македония",
            "Сейшельские острова",
            "Сенегал",
            "Сент-Винсент и Гренадины",
            "Сент-Китс и Невис",
            "Сент-Люсия",
            "Сербия",
            "Сербия и Черногория",
            "Сиам",
            "Сингапур",
            "Сирия",
            "Словакия",
            "Словения",
            "Соломоновы Острова",
            "Сомали",
            "Судан",
            "Суринам",
            "Сьерра-Леоне",
            "Таджикистан",
            "Таиланд",
            "Тайвань",
            "Танзания",
            "Тимор-Лесте",
            "Того",
            "Тонга",
            "Тринидад и Тобаго",
            "Тувалу",
            "Тунис",
            "Туркменистан",
            "Турция",
            "Уганда",
            "Узбекистан",
            "Украина",
            "Уругвай",
            "Фарерские острова",
            "Федеративные Штаты Микронезии",
            "Фиджи",
            "Филиппины",
            "Финляндия",
            "Фолклендские острова",
            "Франция",
            "Французская Гвиана",
            "Французская Полинезия",
            "Хорватия",
            "ЦАР",
            "Чад",
            "Черногория",
            "Чехия",
            "Чехословакия",
            "Чили",
            "Швейцария",
            "Швеция",
            "Шри-Ланка",
            "Эквадор",
            "Экваториальная Гвинея",
            "Эритрея",
            "Эстония",
            "Эфиопия",
            "ЮАР",
            "Югославия",
            "Югославия (ФР)",
            "Ямайка",
            "Япония"
        )
    }
}
