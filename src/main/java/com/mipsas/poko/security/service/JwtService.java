package com.mipsas.poko.security.service;

import com.mipsas.poko.security.jwt.JwtUser;
import java.util.Date;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String createToken(JwtUser user);
    Authentication parseToken(String token);
    Date getExpirationDate(final String token);
    boolean isNotExpired(final String token);
}
