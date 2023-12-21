package com.dicoding.counteat.ui.main.fragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.counteat.data.repository.AppRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private  val repository: AppRepository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}