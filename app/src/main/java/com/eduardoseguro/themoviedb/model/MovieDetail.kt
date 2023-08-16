package com.eduardoseguro.themoviedb.model


import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("backdrop_path") val backdropPath: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    val runtime: Int,
    val tagline: String,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) {
    companion object {
        val emptyMovie = MovieDetail(
            "",
            listOf(),
            "",
            0,
            "",
            0.0,
            "",
            "",
            0,
            "",
            "",
            0.0,
            0
        )
    }
}