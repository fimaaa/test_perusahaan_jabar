package com.example.test_jabar.data.response

import com.example.test_jabar.data.model.City
import com.example.test_jabar.data.model.Forecast
import com.google.gson.annotations.SerializedName

data class Forecast5Response(

	@field:SerializedName("city")
	val city: City? = null,

	@field:SerializedName("cnt")
	val cnt: Int? = null,

	@field:SerializedName("cod")
	val cod: String? = null,

	@field:SerializedName("message")
	val message: Double? = null,

	@field:SerializedName("list")
	val list: MutableList<Forecast?>? = null
)