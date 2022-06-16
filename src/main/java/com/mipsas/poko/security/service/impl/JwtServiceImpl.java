package com.mipsas.poko.security.service.impl;

import static com.mipsas.poko.api.exception.ErrorStatus.ACCESS_DENIED;
import static com.mipsas.poko.security.filter.JwtAuthorizationFilter.BEARER;
import com.mipsas.poko.security.jwt.JwtUser;
import com.mipsas.poko.security.jwt.UserAndClaims;
import com.mipsas.poko.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.startsWith;
import org.springframework.beans.factory.annotation.Value;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String ROLE_KEY = "role";
    public static final String TYP_KEY = "typ";
    public static final String TYP_VALUE = "JWT";
    public static final String ISSUER = "Poko-Game";

    @Value("${jwt.secret}")
    private String secret;

    @Value("#{${jwt.expiration} * 60 * 1000}")
    private long tokenValidityInMillis;

    @Override
    public String createToken(JwtUser user) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setHeaderParam(TYP_KEY, TYP_VALUE)
                .setIssuer(ISSUER)
                .setIssuedAt(Date.from(now))
                .setSubject(user.getNickName())
                .setExpiration(Date.from(now.plusMillis(tokenValidityInMillis)))
                .claim(ROLE_KEY, user.getRole().name())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(h -> startsWith(h, BEARER))
                .map(h -> h.replaceFirst(BEARER, ""))
                .orElse(null);
    }

    @Override
    public Authentication getAuthenticationToken(UserAndClaims userAndClaims) {
        String userName = userAndClaims.getUserName();
        return new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority(ROLE_PREFIX + userAndClaims.getTokenClaims().get(ROLE_KEY))));
    }

    @Override
    public UserAndClaims getUserNameWithClaims(String token) {
        return UserAndClaims.builder()
                .userName(getClaim(token, Claims::getSubject))
                .tokenClaims(getClaims(token)).build();
    }

    @Override
    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isNotExpired(String token) {
        return isNotBlank(token) && getExpirationDate(token).after(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaims(token));
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw ACCESS_DENIED.getException();
        }
    }
}
