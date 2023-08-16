package com.eduardoseguro.themoviedb.api

import com.eduardoseguro.themoviedb.model.MovieDetail
import com.eduardoseguro.themoviedb.model.MovieResponse

class MovieRepository(
    private val movieService: MovieService,
    private val responseHandler: ResponseHandler
) {

    suspend fun getMainMovieList(): Resource<MutableList<MovieResponse>> {
        val movieList = mutableListOf<MovieResponse>()
        try {
            movieService.getNowPlayingMovieList().apply {
                this.category = "Now Playing"
                movieList.add(this)
            }
            movieService.getPopularMovieList().apply {
                this.category = "Popular"
                movieList.add(this)
            }
            movieService.getTopRatedMovieList().apply {
                this.category = "Top Rated"
                movieList.add(this)
            }
            movieService.getUpcomingMovieList().apply {
                this.category = "Upcoming"
                movieList.add(this)
            }
        } catch (e: Exception) {
            return responseHandler.handleException(e)
        }
        return responseHandler.handleSuccess(movieList)
    }

    suspend fun getPopularMovieList(): Resource<MovieResponse> {
        return try {
            responseHandler.handleSuccess(movieService.getPopularMovieList())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getNowPlayingMovieList(): Resource<MovieResponse> {
        return try {
            responseHandler.handleSuccess(movieService.getNowPlayingMovieList())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getTopRatedMovieList(): Resource<MovieResponse> {
        return try {
            responseHandler.handleSuccess(movieService.getTopRatedMovieList())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getUpcomingMovieList(): Resource<MovieResponse> {
        return try {
            responseHandler.handleSuccess(movieService.getUpcomingMovieList())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> {
        return try {
            responseHandler.handleSuccess(movieService.getMovieDetails(movieId))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getMovieByQuery(query: String): Resource<MovieResponse> {
        return try {
            responseHandler.handleSuccess(movieService.getMovieByQuery(query))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}