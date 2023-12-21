package com.dicoding.counteat.data.pref

data class RegisterModel (
    var username: String,
    var email: String,
    var password: String,
    var gender: String,
    val token: String,
    val isRegister: Boolean = false
)
