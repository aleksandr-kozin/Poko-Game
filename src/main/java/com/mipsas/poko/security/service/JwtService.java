package com.mipsas.poko.security.service;

import com.mipsas.poko.security.jwt.JwtUser;
import com.mipsas.poko.security.jwt.UserAndClaims;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String createToken(JwtUser user);
    Date getExpirationDate(final String token);
    boolean isNotExpired(final String token);
    UserAndClaims getUserNameWithClaims(final String token);
    String resolveToken(final HttpServletRequest request);
    Authentication getAuthenticationToken(UserAndClaims userAndClaims);
}
