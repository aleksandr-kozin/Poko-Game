package com.mipsas.poko.data.entity;

import java.time.Instant;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "meta_data")
public class MetaDataEntity extends BaseEntity<Long> {

    private String ip;
    private String provider;

    @Column(name = "system_number")
    private Long systemNumber;

    @Builder.Default
    private Instant date = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
