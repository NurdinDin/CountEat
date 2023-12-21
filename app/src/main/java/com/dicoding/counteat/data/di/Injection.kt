package com.dicoding.counteat.data.di

import android.content.Context
import com.dicoding.counteat.data.api.ApiConfig
import com.dicoding.counteat.data.pref.UserPreference
import com.dicoding.counteat.data.pref.dataStore
import com.dicoding.counteat.data.repository.AppRepository

object Injection {
    fun provideRepository(context: Context): AppRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return AppRepository.getInstance(apiService, pref)
    }
}