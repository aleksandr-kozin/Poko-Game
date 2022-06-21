package com.mipsas.poko.config;

import static com.mipsas.poko.api.Paths.AUTH;
import com.mipsas.poko.api.service.LocationService;
import com.mipsas.poko.api.service.MetaDataService;
import com.mipsas.poko.security.filter.JwtAuthorizationFilter;
import com.mipsas.poko.security.filter.UserLocationFilter;
import com.mipsas.poko.security.filter.UserMetaDataFilter;
import com.mipsas.poko.security.jwt.JwtUserDetailsService;
import com.mipsas.poko.security.service.JwtBlackListService;
import com.mipsas.poko.security.service.impl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtUserDetailsService userDetailsService;
    private final JwtServiceImpl jwtService;
    private final LocationService locationService;
    private final MetaDataService metaDataService;
    private final JwtBlackListService jwtBlackListService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH + "/**").permitAll()
                .antMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtService, jwtBlackListService));
//                .addFilterAfter(new UserLocationFilter(locationService), BasicAuthenticationFilter.class)
//                .addFilterAfter(new UserMetaDataFilter(metaDataService), BasicAuthenticationFilter.class);
    }
}
