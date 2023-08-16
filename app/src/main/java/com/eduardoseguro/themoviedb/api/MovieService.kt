package com.eduardoseguro.themoviedb.api

import com.eduardoseguro.themoviedb.model.Genre
import com.eduardoseguro.themoviedb.model.MovieDetail
import com.eduardoseguro.themoviedb.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovieList(): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovieList(): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovieList(): MovieResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): List<Genre>

    @GET("discover/movie")
    suspend fun getDiscoverMovieByGenreList(@Query("with_genres") genreId: Int): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): MovieDetail

    @GET("search/movie")
    suspend fun getMovieByQuery(@Query("query") query: String): MovieResponse
}