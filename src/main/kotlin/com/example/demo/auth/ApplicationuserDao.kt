package com.example.demo.auth

interface ApplicationuserDao {
    fun selectApplicationUserByUsername(usename: String): ApplicationUser?
}