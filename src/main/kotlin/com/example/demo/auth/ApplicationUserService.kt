package com.example.demo.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class ApplicationUserService(
    private val applicationUserDao: ApplicationuserDao // これを変えられる
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return applicationUserDao.selectApplicationUserByUsername(username) ?: throw UsernameNotFoundException(
            "User $username does not exist"
        )
    }
}