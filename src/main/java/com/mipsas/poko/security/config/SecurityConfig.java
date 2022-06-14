package com.mipsas.poko.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.mipsas.poko.api.Paths.USER_REGISTRATION;
import com.mipsas.poko.security.jwt.JwtAuthenticationFilter;
import com.mipsas.poko.security.jwt.JwtAuthorizationFilter;
import com.mipsas.poko.security.jwt.JwtUserDetailsService;
import com.mipsas.poko.security.service.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper objectMapper;
    private final JwtUserDetailsService userDetailsService;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(USER_REGISTRATION).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), objectMapper, jwtService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtService));
    }
}
