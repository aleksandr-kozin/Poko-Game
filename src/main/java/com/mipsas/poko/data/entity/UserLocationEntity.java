package com.mipsas.poko.data.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "users_locations")
public class UserLocationEntity extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @OneToOne
    @JoinColumn(name = "network_id")
    private NetworkEntity locationNetwork;

    @ManyToOne
    @JoinColumn(name = "metadata_id")
    private MetaDataEntity metaData;
}
