package com.mipsas.poko.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDetails {
    private String agentName;
    private String agentVersion;
    private String osName;
    private String osVersion;
    private String deviceName;
}
