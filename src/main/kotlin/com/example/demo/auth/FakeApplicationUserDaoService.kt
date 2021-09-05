package com.example.demo.auth

import com.example.demo.security.ApplicationUserRole
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository("fake")
class FakeApplicationUserDaoService(val passwordEncoder: PasswordEncoder) : ApplicationUserDao {
    private fun getUsers(): Set<ApplicationUser> {
        // STUDENT
        val student1 = ApplicationUser(
            username = "student1",
            password = passwordEncoder.encode("password"),
            grantedAuthorities = ApplicationUserRole.STUDENT.getAuthorities()
        )

        // ADMIN
        val admin1 = ApplicationUser(
            username = "admin1",
            password = passwordEncoder.encode("password"),
            grantedAuthorities = ApplicationUserRole.ADMIN.getAuthorities()
        )

        // ADMIN_TRAINEE
        val adminTrainee1 = ApplicationUser(
            username = "adminTrainee1",
            password = passwordEncoder.encode("password"),
            grantedAuthorities = ApplicationUserRole.ADMIN_TRAINEE.getAuthorities()
        )
        return setOf(student1, admin1, adminTrainee1)
    }

    override fun selectApplicationUserByUsername(username: String): ApplicationUser? {
        return getUsers().find { it.username == username }
    }

}