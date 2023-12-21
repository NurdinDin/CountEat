package com.dicoding.counteat.ui.main.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.counteat.data.pref.UserModel
import com.dicoding.counteat.data.repository.AppRepository
import com.dicoding.counteat.data.response.BMRResponse

class HomeViewModel(private val repository: AppRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }


    /*private val _bmrData = MutableLiveData<String>()
    val bmrData: LiveData<String> get() = _bmrData

    fun getBmr() {
        viewModelScope.launch {
            repository.getUsername().collect { username ->
                if (username.isNotEmpty()) {
                    repository.getBmr(username).observeForever { bmrResponse ->
                        bmrResponse?.let {
                            if (it.error == false) {
                                _bmrData.value = it.bmr ?: ""
                            } else {
                                Log.e("HomeFragment", "Error: ${error("error")}")
                                //_errorState.value = it.status ?: "Unknown error"
                                //Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }*/
    private val _bmrUser = MutableLiveData<BMRResponse>()
    val bmrUser : LiveData<BMRResponse> = _bmrUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun findUser(username: String, bmr: String) {
        _isLoading.value = true
        /*val client = ApiConfig.getApiService().getBmr(username, bmr)
        client.enqueue(object : Callback<BMRResponse> {
            override fun onResponse(call: Call<BMRResponse>, response: Response<BMRResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _bmrUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<BMRResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })*/

        /*if (bmr != null) {
            repository.getB
        }*/
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}