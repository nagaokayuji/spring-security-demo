package com.example.demo.security

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class ApplicationUserRole(private val permissions: Set<ApplicationUserPermission>) {
    STUDENT(setOf()),
    ADMIN(
        setOf(
            ApplicationUserPermission.COURSE_READ,
            ApplicationUserPermission.COURSE_WRITE,
            ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.STUDENT_WRITE
        )
    ),
    ADMIN_TRAINEE(
        setOf(
            ApplicationUserPermission.COURSE_READ,
            ApplicationUserPermission.STUDENT_READ
        )
    );

    //    companion object {
    fun getAuthorities(): MutableSet<SimpleGrantedAuthority> {
        val authorities = permissions.map { SimpleGrantedAuthority(it.permission) }.toMutableSet()
        // role も追加しておく
        authorities.add(SimpleGrantedAuthority("ROLE_" + this.name))
        return authorities
    }
}

