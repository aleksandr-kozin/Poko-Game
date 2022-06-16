package com.mipsas.poko.security.jwt;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class UserAndClaims {
    String userName;
    Claims tokenClaims;
}
