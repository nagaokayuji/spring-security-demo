package com.example.demo.auth

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ApplicationUserService(
    @Qualifier("fake") private val applicationUserDao: ApplicationUserDao // これを変えられる
) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return applicationUserDao.selectApplicationUserByUsername(username) ?: throw UsernameNotFoundException(
            "User $username does not exist"
        )
    }
}