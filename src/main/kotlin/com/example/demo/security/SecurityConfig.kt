package com.example.demo.security

import com.example.demo.auth.ApplicationUserService
import com.example.demo.jwt.JwtConfig
import com.example.demo.jwt.JwtTokenVerifier
import com.example.demo.jwt.JwtUsernameAndPasswordAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import javax.crypto.SecretKey

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val passwordEncoder: PasswordEncoder,
    private val applicationUserService: ApplicationUserService,
    private val secretKey: SecretKey,
    private val jwtConfig: JwtConfig
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
            .addFilterAfter(
                JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter::class.java
            )
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
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .permitAll()
//            .defaultSuccessUrl("/courses", true)
//            .passwordParameter("password")
//            .usernameParameter("username")
//            .and()
//            .rememberMe()
//            .tokenValiditySeconds(TimeUnit.DAYS.toSeconds(1).toInt()) // 1 day
//            .key("hoge")
//            .userDetailsService(applicationUserService) // これがないと動かない？
//            .rememberMeParameter("remember-me")
//            .and()
//            .logout()
//            .logoutUrl("/logout")
//            .logoutRequestMatcher(
//                AntPathRequestMatcher(
//                    "/logout",
//                    "GET"
//                )
//            )
//            .clearAuthentication(true)
//            .invalidateHttpSession(true)
//            .deleteCookies("JSESSIONID", "remember-me")
//            .logoutSuccessUrl("/login")
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(applicationUserService)
        return provider
    }
}
