package com.mipsas.poko.security.service;

import static com.mipsas.poko.api.exception.ErrorStatus.ACCESS_DENIED;
import com.mipsas.poko.security.jwt.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public Authentication parseToken(String token) {
        try {
            Claims parsedToken = getClaims(token);

            String nickName = parsedToken.getSubject();

            return new UsernamePasswordAuthenticationToken(nickName, null, List.of(new SimpleGrantedAuthority(ROLE_PREFIX + parsedToken.get(ROLE_KEY))));
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Date getExpirationDate(final String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isNotExpired(final String token) {
        return getExpirationDate(token).after(new Date());
    }

    private <T> T getClaim(final String token, final Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getClaims(token));
    }

    private Claims getClaims(final String token) {
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
