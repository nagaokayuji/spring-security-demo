package com.example.demo.jwt

data class UsernameAndPasswordAuthenticationRequest(
    var username: String? = null,
    var password: String? = null
)
