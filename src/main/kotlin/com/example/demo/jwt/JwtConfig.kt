package com.example.demo.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "application.jwt")
@Component
class JwtConfig {
    private var secretKey: String = "secret"
    private var tokenPrefix: String = "Bearer "
    private var tokenExpirationAfterDays: Int = 423423423

    fun getSecretKey(): String {
        return secretKey
    }

    fun setSecretKey(secretKey: String) {
        this.secretKey = secretKey
    }

    fun getTokenPrefix(): String {
        return tokenPrefix
    }

    fun setTokenPrefix(tokenPrefix: String) {
        this.tokenPrefix = tokenPrefix
    }

    fun getTokenExpirationAfterDays(): Int {
        return tokenExpirationAfterDays
    }

    fun setTokenExpirationAfterDays(tokenExpirationAfterDays: Int) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays
    }

    fun getAuthorizationHeader(): String {
        return HttpHeaders.AUTHORIZATION
    }
}