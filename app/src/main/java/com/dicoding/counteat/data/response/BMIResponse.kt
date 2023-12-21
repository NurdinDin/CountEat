package com.dicoding.counteat.data.response

import com.google.gson.annotations.SerializedName

data class BMIResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
