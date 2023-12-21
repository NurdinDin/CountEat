package com.dicoding.counteat.ui.insert_tb_bb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.counteat.data.pref.RegisterModel
import com.dicoding.counteat.data.repository.AppRepository
import kotlinx.coroutines.launch

class TBbBbViewModel(private val repository: AppRepository): ViewModel() {

    fun getRegisterSession(): LiveData<RegisterModel> {
        return repository.getRegisterSession().asLiveData()
    }

    fun tbUser(username: String, tb: String, bb: String, age: String) =
        repository.bmiUser(username, tb, bb, age)

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    fun getUsername() {
        viewModelScope.launch {
            repository.getUsername().collect {
                _username.value = it
            }
        }
    }

    init {
        getUsername()
    }
}