package com.example.demo.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.concurrent.TimeUnit

@Configuration
@EnableWebSecurity
class SecurityConfig(private val passwordEncoder: PasswordEncoder) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
            .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name)
            .antMatchers(HttpMethod.DELETE, "/management/api/**")
            .hasAuthority(ApplicationUserPermission.COURSE_WRITE.permission)
            .antMatchers(HttpMethod.POST, "/management/api/**")
            .hasAuthority(ApplicationUserPermission.COURSE_WRITE.permission)
            .antMatchers(HttpMethod.PUT, "/management/api/**")
            .hasAuthority(ApplicationUserPermission.COURSE_WRITE.permission)
            .antMatchers("/management/api/**")
            .hasAnyRole(ApplicationUserRole.ADMIN.name, ApplicationUserRole.ADMIN_TRAINEE.name)
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/courses", true)
            .passwordParameter("password")
            .usernameParameter("username")
            .and()
            .rememberMe()
            .tokenValiditySeconds(TimeUnit.DAYS.toSeconds(1).toInt()) // 1 day
            .key("hoge")
            .rememberMeParameter("remember-me")
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutRequestMatcher(
                AntPathRequestMatcher(
                    "/logout",
                    "GET"
                )
            )
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID", "remember-me")
            .logoutSuccessUrl("/login")
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        // STUDENT
        val student1 = User.builder()
            .username("student1")
            .password(passwordEncoder.encode("password"))
            .authorities(ApplicationUserRole.STUDENT.getAuthorities())
            .build()
        // ADMIN
        val admin1 = User.builder()
            .username("admin1")
            .password(passwordEncoder.encode("password"))
            .authorities(ApplicationUserRole.ADMIN.getAuthorities())
            .build()
        // ADMIN_TRAINEE
        val adminTrainee1 = User.builder()
            .username("adminTrainee1")
            .password(passwordEncoder.encode("password"))
            .authorities(ApplicationUserRole.ADMIN_TRAINEE.getAuthorities())
            .build()

        return InMemoryUserDetailsManager(
            student1,
            admin1,
            adminTrainee1
        )
    }
}
