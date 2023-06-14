package com.example.lapakkita_android.data.remote.response

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

data class AllStoreResponse(
	@field:SerializedName("AllStoreResponse")

	var allStoreResponse: List<AllStoreResponseItem>
)

@IgnoreExtraProperties
data class AllStoreResponseItem(
	val jumlah_rating: Int = 0,
	val id_toko: Int = 0,
	val latitude: Double = -6.350253,
	val rerata_rating: Double = 0.0,
	val kategori: String = "",
	val deskripsi: String = "",
	val longitude: Double = 106.892799,
	val nama_toko: String = "",
	val url_image: String = ""
){

}
