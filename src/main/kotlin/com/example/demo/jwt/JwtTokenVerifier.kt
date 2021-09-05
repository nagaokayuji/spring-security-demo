package com.example.demo.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenVerifier(
    private val secretKey: SecretKey,
    private val jwtConfig: JwtConfig
) : OncePerRequestFilter() {
    /**
     * filter
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader())

        if (authorizationHeader.isNullOrBlank() || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "")

        try {
            val claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
            val body = claimsJws.body
            val username = body.subject
            val authorities = body["authorities"] as List<Map<String, String>>?
            val simpleGrantedAuthorities = authorities
                ?.map { m: Map<String, String> ->
                    SimpleGrantedAuthority(
                        m["authority"]
                    )
                }?.toSet()
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                username,
                null,
                simpleGrantedAuthorities
            )
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: JwtException) {
            throw IllegalStateException(String.format("Token %s cannot be trusted", token))
        }

        filterChain.doFilter(request, response)
    }
}