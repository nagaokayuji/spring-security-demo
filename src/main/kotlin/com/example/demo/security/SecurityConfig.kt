package com.example.demo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SecurityConfig(private val passwordEncoder: PasswordEncoder) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name)
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        // STUDENT
        val student1 = User.builder()
            .username("student1")
            .password(passwordEncoder.encode("password"))
            .roles(ApplicationUserRole.STUDENT.name)
            .build()
        // ADMIN
        val admin1 = User.builder()
            .username("admin1")
            .password(passwordEncoder.encode("password"))
            .roles(ApplicationUserRole.ADMIN.name)
            .build()
        // ADMIN_TRAINEE
        val adminTrainee1 = User.builder()
            .username("adminTrainee1")
            .password(passwordEncoder.encode("password"))
            .roles(ApplicationUserRole.ADMIN_TRAINEE.name)
            .build()

        return InMemoryUserDetailsManager(
            student1,
            admin1,
            adminTrainee1
        )
    }
}
