package com.gesvik.movieapp.network

import com.gesvik.movieapp.network.entities.FilmsProperty
import com.gesvik.movieapp.network.entities.FilmItemProperty
import com.gesvik.movieapp.network.entities.GenresResponse
import com.gesvik.movieapp.network.entities.StaffProperty
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

//private const val BASE_URL = "https://api.kinopoisk.cloud/"
private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client.build())
    .build()

interface FilmApiService {
    @Headers("X-API-KEY: bbbae289-8105-4e47-8682-a63c9bfc4238")
    @GET("v2.2/films/top?page=1")
    suspend fun getFilms(@Query("type") type: String): FilmsProperty

    @Headers("X-API-KEY: bbbae289-8105-4e47-8682-a63c9bfc4238")
    @GET("v2.1/films/{id}")
    suspend fun getFilmItem(@Path("id") id: Int): FilmItemProperty

    @Headers("X-API-KEY: bbbae289-8105-4e47-8682-a63c9bfc4238")
    @GET("v1/staff")
    suspend fun getStaff(@Query("filmId") id: Int): List<StaffProperty>

    @Headers("X-API-KEY: bbbae289-8105-4e47-8682-a63c9bfc4238")
    @GET("v2.1/films/filters")
    suspend fun getGenres(): GenresResponse
}

object FilmApi { val retrofitService: FilmApiService by lazy {
        retrofit.create(FilmApiService::class.java)
    }
}