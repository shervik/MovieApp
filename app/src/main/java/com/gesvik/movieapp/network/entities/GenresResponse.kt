package com.gesvik.movieapp.network.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresResponse(

	@Json(name="genres")
	val genres: List<Genres>?,

	@Json(name="countries")
	val countries: List<Countries>
) : Parcelable

@Parcelize
data class Genres(

	@Json(name="genre")
	val genre: String?,

	@Json(name="id")
	val id: Int
) : Parcelable

@Parcelize
data class Countries(

	@Json(name="country")
	val country: String?,

	@Json(name="id")
	val id: Int
) : Parcelable
