package com.eduardoseguro.themoviedb.di

import com.eduardoseguro.themoviedb.ConstantsAPI
import com.eduardoseguro.themoviedb.api.MovieRepository
import com.eduardoseguro.themoviedb.api.MovieService
import com.eduardoseguro.themoviedb.api.ResponseHandler
import com.eduardoseguro.themoviedb.details.MovieDetailsViewModel
import com.eduardoseguro.themoviedb.movie.MovieListViewModel
import com.eduardoseguro.themoviedb.search.MovieSearchViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val movieModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { MovieSearchViewModel(get()) }
    factory { MovieRepository(get(), get()) }
}

val networkModule = module {
    factory { provideInterceptor() }
    factory { provideHttpClient(get()) }
    single { provideRetrofit(get()) }
    factory { ResponseHandler() }
    factory { provideMovieService(get()) }
}

fun provideInterceptor(): Interceptor {
    return Interceptor {
        val url = it.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", ConstantsAPI.API_KEY)
            .build()

        val request = it.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor it.proceed(request)
    }
}

fun provideHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .connectTimeout(30, TimeUnit.SECONDS)
    .build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(ConstantsAPI.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)