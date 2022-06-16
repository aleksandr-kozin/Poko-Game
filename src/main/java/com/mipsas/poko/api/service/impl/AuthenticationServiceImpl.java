package com.mipsas.poko.api.service.impl;

import com.mipsas.poko.api.service.AuthenticationService;
import com.mipsas.poko.data.entity.JwtBlackListEntity;
import com.mipsas.poko.security.service.JwtBlackListService;
import com.mipsas.poko.security.service.JwtService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtBlackListService jwtBlackListService;
    private final JwtService jwtService;

    @Override
    public void signOut(HttpServletRequest request) {
        String token = jwtService.resolveToken(request);
        jwtBlackListService.addToBlackList(JwtBlackListEntity.builder()
                .token(token)
                .expirationDate(jwtService.getExpirationDate(token))
                .build());
    }
}
