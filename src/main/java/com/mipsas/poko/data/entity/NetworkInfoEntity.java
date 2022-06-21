package com.mipsas.poko.data.entity;

import com.mipsas.poko.common.enums.NetworkType;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "network_connections")
public class NetworkInfoEntity extends BaseEntity<Long> {

    @Column(name = "network_type")
    private NetworkType networkType;

    @Column(name = "signal_strength")
    private Integer signalStrength;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
