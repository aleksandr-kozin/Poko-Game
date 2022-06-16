package com.mipsas.poko.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.mipsas.poko.api.Paths.USER_REGISTRATION;
import com.mipsas.poko.api.service.UserLocationService;
import com.mipsas.poko.api.service.UserMetaDataService;
import com.mipsas.poko.security.filter.JwtAuthenticationFilter;
import com.mipsas.poko.security.filter.JwtAuthorizationFilter;
import com.mipsas.poko.security.filter.UserLocationFilter;
import com.mipsas.poko.security.filter.UserMetaDataFilter;
import com.mipsas.poko.security.jwt.JwtUserDetailsService;
import com.mipsas.poko.security.service.JwtBlackListService;
import com.mipsas.poko.security.service.impl.JwtServiceImpl;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper objectMapper;
    private final JwtUserDetailsService userDetailsService;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserLocationService userLocationService;
    private final UserMetaDataService userMetaDataService;
    private final JwtBlackListService jwtBlackListService;

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
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtService, jwtBlackListService))
                .addFilterAfter(new UserLocationFilter(userLocationService), BasicAuthenticationFilter.class)
                .addFilterAfter(new UserMetaDataFilter(userMetaDataService), BasicAuthenticationFilter.class);
    }
}
