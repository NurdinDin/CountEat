package com.dicoding.counteat.ui.factory

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.counteat.data.di.Injection
import com.dicoding.counteat.data.repository.AppRepository
import com.dicoding.counteat.ui.Register.login.LoginViewModel
import com.dicoding.counteat.ui.Register.signup.SignUpViewModel
import com.dicoding.counteat.ui.insert_tb_bb.TBbBbViewModel
import com.dicoding.counteat.ui.main.MainViewModel
import com.dicoding.counteat.ui.main.fragment.home.HomeViewModel
import com.dicoding.counteat.ui.main.fragment.profile.ProfileViewModel

class ViewModelFactory(private val repository: AppRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TBbBbViewModel::class.java) -> {
                TBbBbViewModel(repository) as T
            }
            else -> throw  IllegalStateException("Unknow ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context) : ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }

        @JvmStatic
        fun getInstance(fragment: Fragment): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(fragment.requireContext()))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

}