package com.example.demo.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class ApplicationUser(
    private val username: String,
    private val password: String,
    private val grantedAuthorities: MutableSet<out GrantedAuthority>,
    private val isAccountNonExpired: Boolean = true,
    private val isAccountNonLocked: Boolean = true,
    private val isEnabled: Boolean = true,
    private val isCredentialsNonExpired: Boolean = true
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return grantedAuthorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }
}
