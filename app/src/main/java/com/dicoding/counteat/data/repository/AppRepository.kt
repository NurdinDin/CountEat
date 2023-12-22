package com.dicoding.counteat.data.repository

import androidx.lifecycle.liveData
import com.dicoding.counteat.data.api.ApiService
import com.dicoding.counteat.data.api.bmiUserRequest
import com.dicoding.counteat.data.pref.RegisterModel
import com.dicoding.counteat.data.pref.UserModel
import com.dicoding.counteat.data.pref.UserPreference
import com.dicoding.counteat.data.response.ErrorResponse
import com.dicoding.counteat.data.response.LoginResponse
import com.dicoding.counteat.data.state.State
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AppRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
){

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun registerSaveSession(register: RegisterModel) {
        userPreference.registerSaveSession(register)
    }

    fun getUsername(): Flow<String> {
        return userPreference.getUsername()
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getRegisterSession(): Flow<RegisterModel> {
        return userPreference.getRegisterSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(name: String, email: String, password: String, gender: String) = liveData{
        emit(State.Loading)
        try {
            val successResponse = apiService.register(name, email, password, gender)
            val message = successResponse.message
            emit(State.Success(message))
        } catch (e: HttpException) {
            val errorMessage: String
            if (e.code() == 400) {
                errorMessage = "Email telah digunakan"
                emit(State.Error(errorMessage))
            } else {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                errorMessage = errorBody.message.toString()
                emit(State.Error(errorMessage))
            }
        }
    }

    fun loginUser(email: String, password: String) = liveData {
        emit(State.Loading)
        try {
            val responseSuccess = apiService.login(email, password)
            val data = responseSuccess.loginResult?.token
            emit(State.Success(data))

        }
        catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
            val errorMessage : String = errorBody.message.toString()
            emit(State.Error(errorMessage))
        }
    }

    fun bmiUser(username: String, tb: String, bb: String, age: String) = liveData {
        emit(State.Loading)
        try {
            val request = bmiUserRequest(tinggibadan = tb, beratbadan = bb, umur = age)
            val responseSuccess = apiService.bmi(username, request)
            val data = responseSuccess.message
            emit(State.Success(data))
        } catch (e: HttpException) {
            emit(State.Error("error: ${e.message}"))
        }
    }

    /*fun getBmr(username: String, bmr: String) = liveData<State<BMRResponse>> {
        emit(State.Loading)
        try {
            val responseSuccess = apiService.getBmr(username, bmr)
            val data = responseSuccess.bo
        }
    }*/

    /*private val _bmr = MutableLiveData<BMRResponse>()
    val bmr: LiveData<BMRResponse> = _bmr

    fun getBmr(username: String, getBmr: String) = liveData {
        emit(State.Loading)
        try {
            val request = getUserBmr(bmr = getBmr)
            val responseSuccess = apiService.getBmr(username, request)
            val data = responseSuccess.bmr
            emit(State.Success(data))
        } catch (e: HttpException) {
            emit(State.Error("error: ${e.message}"))
        }
    }*/

    //fun getBmr(username: String) = apiService.getBmr()

    companion object {
        private const val TAG = "MainViewModel"
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference) =
            instance ?: synchronized(this) {
                instance ?: AppRepository(apiService, userPreference)
            }.also { instance = it }
    }
}
