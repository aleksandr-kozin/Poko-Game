package com.mipsas.poko.data.entity;

import java.util.Set;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "locations", uniqueConstraints = @UniqueConstraint(columnNames = {"latitude", "longitude"}))
public class LocationEntity extends BaseEntity<Long> {

    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String postal;
    private String state;

    @OneToMany(mappedBy = "location", cascade = REMOVE)
    private Set<NetworkEntity> networks;

    @OneToMany(mappedBy = "location", cascade = REMOVE)
    private Set<UserLocationEntity> userLocation;
}
