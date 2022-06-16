package com.mipsas.poko.security.filter;

import com.mipsas.poko.security.service.JwtBlackListService;
import com.mipsas.poko.security.service.JwtService;
import com.mipsas.poko.security.service.impl.JwtServiceImpl;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public static final String BEARER = "Bearer ";

    private final JwtService jwtService;
    private final JwtBlackListService jwtBlackListService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtServiceImpl jwtService, JwtBlackListService jwtBlackListService) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.jwtBlackListService = jwtBlackListService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional.ofNullable(jwtService.resolveToken(request))
                .filter(jwtService::isNotExpired)
                .filter(jwtBlackListService::isNotBlacklisted)
                .map(jwtService::getUserNameWithClaims)
                .map(jwtService::getAuthenticationToken)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        chain.doFilter(request, response);
    }
}
