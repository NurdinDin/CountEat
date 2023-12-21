package com.dicoding.counteat.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.counteat.data.pref.UserModel
import com.dicoding.counteat.data.repository.AppRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return  repository.getSession().asLiveData()
    }

   // fun getBmr(username: String) = repository.getBmr(username)

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    fun getUsername() {
        viewModelScope.launch {
            repository.getUsername().collect {
                _username.value
            }
        }
    }
}