package com.dicoding.counteat.data.pref

data class UserModel (
    var email: String,
    var password: String,
    val token: String,
    val isLogin: Boolean = false
)
