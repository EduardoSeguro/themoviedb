package com.eduardoseguro.themoviedb.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eduardoseguro.themoviedb.api.MovieRepository
import com.eduardoseguro.themoviedb.api.Resource
import com.eduardoseguro.themoviedb.model.MovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val resourceLiveData: MutableLiveData<Resource<MutableList<MovieResponse>>> by lazy {
        MutableLiveData<Resource<MutableList<MovieResponse>>>()
    }

    fun getMovieList() {
        resourceLiveData.postValue(Resource.loading(null))
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieRepository.getMainMovieList()
            resourceLiveData.postValue(response)
        }
    }
}