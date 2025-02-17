package com.sb.moovich.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PersonDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("enProfession")
    val enProfession: String?,
)
