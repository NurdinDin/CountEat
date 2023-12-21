package com.dicoding.counteat.ui.Register.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.counteat.data.pref.RegisterModel
import com.dicoding.counteat.data.repository.AppRepository
import kotlinx.coroutines.launch

class SignUpViewModel (private val repository: AppRepository): ViewModel() {
    fun saveSessionRegister(user: RegisterModel) {
        viewModelScope.launch {
            repository.registerSaveSession(user)
        }
    }

    fun signUp(name: String, email: String, password: String, gender: String) =
        repository.register(name, email, password, gender)
}