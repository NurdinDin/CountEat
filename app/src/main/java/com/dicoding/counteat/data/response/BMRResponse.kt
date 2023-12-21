package com.dicoding.counteat.data.response

import com.google.gson.annotations.SerializedName

data class BMRResponse(

	@field:SerializedName("bmr")
	val bmr: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: String? = null
)
