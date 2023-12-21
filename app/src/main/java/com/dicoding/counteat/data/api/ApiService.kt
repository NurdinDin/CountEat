package com.dicoding.counteat.data.api

import com.dicoding.counteat.data.response.BMIResponse
import com.dicoding.counteat.data.response.BMRResponse
import com.dicoding.counteat.data.response.LoginResponse
import com.dicoding.counteat.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class bmiUserRequest(
    val tinggibadan: String,
    val beratbadan: String,
    val umur: String
)

data class GetUserBmr(
    val bmr: String
)

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @POST("updatedetail/{username}")
    suspend fun bmi(
        @Path("username") username: String,
        @Body request: bmiUserRequest
    ) : BMIResponse

    @GET("getbmr/{username}")
    fun getBmr(
        @Path("username") username: String,
        @Query("bmr") bmr: String
    ): Call<BMRResponse>
}