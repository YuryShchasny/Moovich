package com.sb.moovich.data.model

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("photo")
    val photo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("enProfession")
    val enProfession: String
)