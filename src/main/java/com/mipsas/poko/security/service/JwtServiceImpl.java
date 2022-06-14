package com.mipsas.poko.security.service;

import com.mipsas.poko.security.jwt.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import java.util.List;
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
            Claims parsedToken = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String nickName = parsedToken.getSubject();

            return new UsernamePasswordAuthenticationToken(nickName, null, List.of(new SimpleGrantedAuthority(ROLE_PREFIX + parsedToken.get(ROLE_KEY))));
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
