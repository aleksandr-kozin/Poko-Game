package com.mipsas.poko.data.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "jwt_blacklist")
public class JwtBlackListEntity extends BaseEntity<Long> {
    private String token;

    @Column(name = "expiration_date")
    private Instant expirationDate;
}
