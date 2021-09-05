package com.example.demo.auth

interface ApplicationUserDao {
    fun selectApplicationUserByUsername(username: String): ApplicationUser?
}