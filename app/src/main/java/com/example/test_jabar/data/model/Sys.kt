package com.example.test_jabar.data.model

import com.google.gson.annotations.SerializedName

data class Sys(

	@field:SerializedName("pod")
	val pod: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: String? = null,

	@field:SerializedName("sunset")
	val sunset: String? = null

)