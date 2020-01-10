package com.example.test_jabar.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("cod")
	val code: String? = null
)