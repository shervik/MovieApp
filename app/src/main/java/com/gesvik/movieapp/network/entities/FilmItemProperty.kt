package com.gesvik.movieapp.network.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmItemProperty(

	@Json(name = "data")
	val data: Data,

	@Json(name = "externalId")
	val externalId: ExternalId
) : Parcelable

@Parcelize
data class Data(

	@Json(name = "premiereDigital")
	val premiereDigital: String?,

	@Json(name = "premiereBluRay")
	val premiereBluRay: String?,

	@Json(name = "year")
	val year: String?,

	@Json(name = "premiereDvd")
	val premiereDvd: String?,

	@Json(name = "filmLength")
	val filmLength: String?,

	@Json(name = "description")
	val description: String?,

	@Json(name = "type")
	val type: String?,

	@Json(name = "facts")
	val facts: List<String>?,

	@Json(name = "nameRu")
	val nameRu: String?,

	@Json(name = "posterUrl")
	val posterUrl: String?,

	@Json(name = "genres")
	val genres: List<GenresItemFilm>?,

	@Json(name = "ratingMpaa")
	val ratingMpaa: String?,

	@Json(name = "ratingAgeLimits")
	val ratingAgeLimits: Int?,

	@Json(name = "seasons")
	val seasons: List<String>?,

	@Json(name = "distributors")
	val distributors: String?,

	@Json(name = "nameEn")
	val nameEn: String?,

	@Json(name = "countries")
	val countries: List<CountriesItemFilm>?,

	@Json(name = "premiereWorld")
	val premiereWorld: String?,

	@Json(name = "webUrl")
	val webUrl: String?,

	@Json(name = "premiereRu")
	val premiereRu: String?,

	@Json(name = "distributorRelease")
	val distributorRelease: String?,

	@Json(name = "filmId")
	val filmId: Int,

	@Json(name = "premiereWorldCountry")
	val premiereWorldCountry: String?,

	@Json(name = "posterUrlPreview")
	val posterUrlPreview: String?,

	@Json(name = "slogan")
	val slogan: String?
) : Parcelable

@Parcelize
data class CountriesItemFilm(

	@Json(name = "country")
	val country: String?
) : Parcelable

@Parcelize
data class GenresItemFilm(

	@Json(name = "genre")
	val genre: String?
) : Parcelable

@Parcelize
data class ExternalId(

	@Json(name = "imdbId")
	val imdbId: String?
) : Parcelable
