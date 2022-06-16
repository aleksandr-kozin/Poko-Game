package com.mipsas.poko.data.entity;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "user_locations")
public class UserLocationEntity extends BaseEntity<Long> {

    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String postal;
    private String state;

    @Builder.Default
    private Instant date = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
