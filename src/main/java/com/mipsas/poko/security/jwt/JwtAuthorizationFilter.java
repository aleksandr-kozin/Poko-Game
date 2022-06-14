package com.mipsas.poko.security.jwt;

import com.mipsas.poko.security.service.JwtServiceImpl;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public static final String BEARER = "Bearer ";

    private final JwtServiceImpl jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtServiceImpl jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(h -> startsWith(h, BEARER))
                .map(h -> h.replaceFirst(BEARER, ""))
                .map(jwtService::parseToken)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        chain.doFilter(request, response);
    }
}
