package com.eduardoseguro.themoviedb.model


import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String,
    val title: String
)