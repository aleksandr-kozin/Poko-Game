package com.mipsas.poko.data.entity;

import com.mipsas.poko.common.enums.NetworkType;
import javax.persistence.*;
import static javax.persistence.EnumType.STRING;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "networks")
public class NetworkEntity extends BaseEntity<Long> {

    @Enumerated(value = STRING)
    @Column(name = "network_type")
    private NetworkType networkType;

    @Column(name = "signal_strength")
    private Integer signalStrength;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;
}
