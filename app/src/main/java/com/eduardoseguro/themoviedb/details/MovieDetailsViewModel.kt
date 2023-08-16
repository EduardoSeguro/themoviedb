package com.eduardoseguro.themoviedb.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eduardoseguro.themoviedb.api.MovieRepository
import com.eduardoseguro.themoviedb.api.Resource
import com.eduardoseguro.themoviedb.model.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val resourceLiveData: MutableLiveData<Resource<MovieDetail>> by lazy {
        MutableLiveData<Resource<MovieDetail>>()
    }

    fun getMovieDetail(movieId: Int) {
        resourceLiveData.postValue(Resource.loading(null))
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieRepository.getMovieDetails(movieId)
            resourceLiveData.postValue(response)
        }
    }
}