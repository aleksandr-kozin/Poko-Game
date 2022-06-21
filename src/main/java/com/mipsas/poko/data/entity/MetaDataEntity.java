package com.mipsas.poko.data.entity;

import javax.persistence.*;
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

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "agent_version")
    private String agentVersion;

    @Column(name = "os_name")
    private String osName;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "device_name")
    private String deviceName;
}
