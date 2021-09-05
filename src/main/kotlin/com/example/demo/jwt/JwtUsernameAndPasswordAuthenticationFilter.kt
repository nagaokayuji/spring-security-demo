package com.example.demo.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.sql.Date
import java.time.LocalDate
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtUsernameAndPasswordAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val jwtConfig: JwtConfig,
    private val secretKey: SecretKey
) : UsernamePasswordAuthenticationFilter() {

    /**
     * 認証
     */
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            val (username, password) = ObjectMapper()
                .readValue(
                    request?.inputStream,
                    UsernameAndPasswordAuthenticationRequest::class.java
                )
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                username,
                password
            )
            return authManager.authenticate(authentication)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * JWT トークン生成
     */
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val token = Jwts.builder()
            .setSubject(authResult!!.name) // subject
            .claim("authorities", authResult.authorities) // body
            .setIssuedAt(java.util.Date())
            .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()!!.toLong())))
            .signWith(secretKey) // 署名
            .compact()
        // header に追加
        response?.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token)
    }
}