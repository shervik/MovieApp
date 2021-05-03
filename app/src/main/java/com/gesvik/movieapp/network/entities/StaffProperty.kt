package com.gesvik.movieapp.network.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class StaffProperty(
	@Json(name="nameRu")
	val nameRu: String?,

	@Json(name="posterUrl")
	val posterUrl: String?,

	@Json(name="description")
	val description: String?,

	@Json(name="professionKey")
	val professionKey: String?,

	@Json(name="professionText")
	val professionText: String?,

	@Json(name="nameEn")
	val nameEn: String?,

	@Json(name="staffId")
	val staffId: Int
) : Parcelable {
	val mNameRu
		get() = nameRu
}
