package com.gesvik.movieapp.network.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class FilmsProperty(

	@Json(name="films")
	val films: List<FilmsItem>,

	@Json(name="pagesCount")
	val pagesCount: Int
)

@Parcelize
data class CountriesItem(

	@Json(name="country")
	val country: String?
): Parcelable

@Parcelize
data class GenresItem(

	@Json(name="genre")
	val genre: String?
): Parcelable

@Parcelize
data class FilmsItem(

	@Json(name="nameRu")
	val nameRu: String?,

	@Json(name="ratingVoteCount")
	val ratingVoteCount: Int?,

	@Json(name="posterUrl")
	val posterUrl: String?,

	@Json(name="year")
	val year: String?,

	@Json(name="genres")
	val genres: List<GenresItem>,

	@Json(name="ratingChange")
	val ratingChange: String?,

	@Json(name="filmId")
	val filmId: Int,

	@Json(name="filmLength")
	val filmLength: String?,

	@Json(name="rating")
	val rating: String?,

	@Json(name="posterUrlPreview")
	val posterUrlPreview: String?,

	@Json(name="nameEn")
	val nameEn: String?,

	@Json(name="countries")
	val countries: List<CountriesItem>
) : Parcelable {
	val mFilmLength
		get() = filmLength

	val mRating
		get() = rating
}
