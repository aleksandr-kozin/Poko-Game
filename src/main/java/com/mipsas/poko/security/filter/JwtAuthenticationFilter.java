package com.mipsas.poko.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.mipsas.poko.api.exception.ErrorStatus.BAD_CREDENTIALS;
import com.mipsas.poko.api.model.request.SignInRequest;
import com.mipsas.poko.security.jwt.JwtUser;
import com.mipsas.poko.security.service.JwtServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final JwtServiceImpl jwtService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, JwtServiceImpl jwtService) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SignInRequest signInRequest = objectMapper.readValue(request.getReader(), SignInRequest.class);

            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password()));
        } catch (IOException e) {
            throw BAD_CREDENTIALS.getException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        JwtUser user = (JwtUser) authResult.getPrincipal();
        String token = jwtService.createToken(user);
        response.addHeader(AUTHORIZATION, token);
    }
}
